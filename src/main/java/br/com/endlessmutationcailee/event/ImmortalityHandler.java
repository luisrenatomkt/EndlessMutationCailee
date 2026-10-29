package br.com.endlessmutationcailee.event;

import br.com.endlessmutationcailee.entity.EntityCailee;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ImmortalityHandler {
    @SubscribeEvent
    public void onAttack(LivingAttackEvent event) {
        if (event.entityLiving instanceof EntityCailee) event.setCanceled(true);
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent event) {
        if (event.entityLiving instanceof EntityCailee) event.setCanceled(true);
    }
}
