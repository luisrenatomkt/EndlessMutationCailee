package br.com.endlessmutationcailee.entity;

import br.com.endlessmutationcailee.config.ModConfig;
import br.com.endlessmutationcailee.evolution.MutationSystem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityCailee extends net.minecraft.entity.EntityCreature {
    private double evolutionEnergy = 0.0D;
    private double muscleMass = 1.0D;
    private double visualScale = 1.0D;
    private int evolutionLevel = 0;
    private int hunger = 0;
    private int huntCooldown = 0;
    private int eatingTicks = 0;
    private int lastConsumedEntityId = -1;

    public EntityCailee(World worldIn) {
        super(worldIn);
        this.setSize(0.6F, 1.8F);
        this.isImmuneToFire = true;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(2, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityAnimal>(this, EntityAnimal.class, 10, true, false, null));
        this.tasks.addTask(2, new AIHuntAnimals(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2048.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(ModConfig.initialSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(ModConfig.initialAttack);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(Math.max(32.0D, ModConfig.searchRadius));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote) {
            if (huntCooldown > 0) huntCooldown--;
            if (eatingTicks > 0) eatingTicks--;
            if (hunger < 1000000) hunger++;
            // Reaplica proteção adicional contra dano letal; a evolução não aumenta com o tempo.
            if (this.getHealth() < this.getMaxHealth()) this.setHealth(this.getMaxHealth());
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        return true;
    }

    @Override
    public boolean canDespawn() {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer player) {
        if (!this.worldObj.isRemote) {
            player.addChatMessage(new net.minecraft.util.ChatComponentText(
                "Cailee | Nível " + evolutionLevel + " | Energia " + format(evolutionEnergy) +
                " | Massa muscular " + format(muscleMass)));
        }
        return true;
    }

    public boolean canHunt() {
        return huntCooldown <= 0;
    }

    public int getHuntRadius() {
        return ModConfig.searchRadius;
    }

    public void beginEating() {
        eatingTicks = 40;
        huntCooldown = ModConfig.searchInterval;
    }

    public void consumePrey(EntityAnimal prey) {
        if (worldObj.isRemote || prey == null || prey.getEntityId() == lastConsumedEntityId) return;
        lastConsumedEntityId = prey.getEntityId();
        double reward = ModConfig.growthPerPrey;
        String name = prey.getName().toLowerCase();
        if (name.contains("cow") || name.contains("horse") || name.contains("donkey")) reward *= 2.0D;
        else if (name.contains("sheep") || name.contains("pig")) reward *= 1.5D;
        evolutionEnergy = safeAdd(evolutionEnergy, reward);
        muscleMass = safeAdd(muscleMass, Math.log1p(reward) * 0.15D);
        evolutionLevel = (int)Math.min(Integer.MAX_VALUE, Math.floor(Math.log1p(evolutionEnergy) / Math.log(2.0D)));
        visualScale = Math.min(ModConfig.maxVisualScale, 1.0D + Math.log1p(muscleMass) * 0.22D);
        double attack = Math.min(1.0E9D, ModConfig.initialAttack + Math.log1p(evolutionEnergy) * 1.5D);
        double speed = Math.min(1.0D, ModConfig.initialSpeed + Math.log1p(evolutionEnergy) * 0.004D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(attack);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
        beginEating();
    }

    private static double safeAdd(double a, double b) {
        double result = a + b;
        return Double.isFinite(result) ? Math.max(0.0D, result) : Double.MAX_VALUE / 4.0D;
    }

    private static String format(double v) {
        if (v >= 1.0E9D) return String.format("%.3e", v);
        return String.format("%.2f", v);
    }

    public double getVisualScale() { return visualScale; }
    public int getEvolutionLevel() { return evolutionLevel; }
    public double getEvolutionEnergy() { return evolutionEnergy; }
    public double getMuscleMass() { return muscleMass; }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setDouble("EMC_Energy", evolutionEnergy);
        tag.setDouble("EMC_MuscleMass", muscleMass);
        tag.setDouble("EMC_VisualScale", visualScale);
        tag.setInteger("EMC_Level", evolutionLevel);
        tag.setInteger("EMC_Hunger", hunger);
        tag.setInteger("EMC_HuntCooldown", huntCooldown);
        tag.setInteger("EMC_EatingTicks", eatingTicks);
        tag.setInteger("EMC_LastConsumedId", lastConsumedEntityId);
        tag.setInteger("EMC_DataVersion", 1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        evolutionEnergy = finiteNonNegative(tag.getDouble("EMC_Energy"), 0.0D);
        muscleMass = finiteNonNegative(tag.getDouble("EMC_MuscleMass"), 1.0D);
        visualScale = finiteNonNegative(tag.getDouble("EMC_VisualScale"), 1.0D);
        evolutionLevel = Math.max(0, tag.getInteger("EMC_Level"));
        hunger = Math.max(0, tag.getInteger("EMC_Hunger"));
        huntCooldown = Math.max(0, tag.getInteger("EMC_HuntCooldown"));
        eatingTicks = Math.max(0, tag.getInteger("EMC_EatingTicks"));
        lastConsumedEntityId = tag.getInteger("EMC_LastConsumedId");
    }

    private static double finiteNonNegative(double v, double fallback) {
        return Double.isNaN(v) || Double.isInfinite(v) || v < 0 ? fallback : v;
    }
}
