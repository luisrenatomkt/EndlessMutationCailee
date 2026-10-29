package br.com.endlessmutationcailee.entity;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.AxisAlignedBB;
import java.util.List;

public class AIHuntAnimals extends EntityAIBase {
    private final EntityCailee cailee;
    private EntityAnimal target;
    private int attackCooldown;

    public AIHuntAnimals(EntityCailee entity) {
        this.cailee = entity;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if (cailee.worldObj.isRemote || !cailee.canHunt()) return false;
        List<EntityAnimal> list = cailee.worldObj.getEntitiesWithinAABB(EntityAnimal.class,
            cailee.getEntityBoundingBox().expand(cailee.getHuntRadius(), 8.0D, cailee.getHuntRadius()));
        EntityAnimal best = null;
        double bestDistance = Double.MAX_VALUE;
        for (EntityAnimal animal : list) {
            if (animal == cailee || animal.isDead || animal.getHealth() <= 0.0F) continue;
            double d = cailee.getDistanceSqToEntity(animal);
            if (d < bestDistance) { best = animal; bestDistance = d; }
        }
        target = best;
        return target != null;
    }

    @Override
    public boolean continueExecuting() {
        return target != null && !target.isDead && cailee.getDistanceSqToEntity(target) < (double)(cailee.getHuntRadius() * cailee.getHuntRadius());
    }

    @Override
    public void startExecuting() {
        attackCooldown = 0;
        cailee.getNavigator().tryMoveToEntityLiving(target, 1.15D);
    }

    @Override
    public void updateTask() {
        if (target == null || target.isDead) return;
        cailee.getLookHelper().setLookPositionWithEntity(target, 30.0F, 30.0F);
        cailee.getNavigator().tryMoveToEntityLiving(target, 1.15D);
        if (attackCooldown > 0) attackCooldown--;
        if (cailee.getDistanceSqToEntity(target) <= 3.2D && attackCooldown <= 0) {
            float before = target.getHealth();
            boolean hit = cailee.attackEntityAsMob(target);
            attackCooldown = 20;
            if (hit && before > 0.0F && target.getHealth() <= 0.0F) {
                cailee.consumePrey(target);
                target = null;
            }
        }
    }

    @Override
    public void resetTask() {
        target = null;
        cailee.getNavigator().clearPathEntity();
    }
}
