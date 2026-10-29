package br.com.endlessmutationcailee.item;

import br.com.endlessmutationcailee.entity.EntityCailee;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemCaileeSummoner extends Item {
    public ItemCaileeSummoner() {
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            EntityCailee entity = new EntityCailee(world);
            entity.setLocationAndAngles(player.posX + player.getLookVec().xCoord * 2.0D,
                player.posY, player.posZ + player.getLookVec().zCoord * 2.0D, player.rotationYaw, 0.0F);
            world.spawnEntityInWorld(entity);
        }
        return stack;
    }
}
