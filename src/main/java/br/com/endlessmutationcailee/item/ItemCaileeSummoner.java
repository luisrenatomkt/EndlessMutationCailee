package br.com.endlessmutationcailee.item;

import br.com.endlessmutationcailee.entity.EntityCailee;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemCaileeSummoner extends Item {
    public ItemCaileeSummoner() {
        setMaxStackSize(1);
        setUnlocalizedName("cailee_summoner");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            Vec3 look = player.getLookVec();
            double x = player.posX + look.xCoord * 2.0D;
            double y = player.posY;
            double z = player.posZ + look.zCoord * 2.0D;

            EntityCailee entity = new EntityCailee(world);
            entity.setLocationAndAngles(x, y, z, player.rotationYaw, 0.0F);
            boolean spawned = world.spawnEntityInWorld(entity);
            if (spawned) {
                player.addChatMessage(new ChatComponentText("Cailee foi invocada!"));
            } else {
                player.addChatMessage(new ChatComponentText("Nao foi possivel invocar a Cailee neste local. Tente em uma area aberta."));
            }
        }
        return stack;
    }
}
