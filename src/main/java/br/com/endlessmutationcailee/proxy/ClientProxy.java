package br.com.endlessmutationcailee.proxy;

import br.com.endlessmutationcailee.client.RenderCailee;
import br.com.endlessmutationcailee.entity.EntityCailee;
import br.com.endlessmutationcailee.item.ModItems;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        if (ModItems.caileeSummoner != null) {
            ModelLoader.setCustomModelResourceLocation(ModItems.caileeSummoner, 0,
                new ModelResourceLocation("endlessmutationcailee:cailee_summoner", "inventory"));
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityCailee.class,
            manager -> new RenderCailee((RenderManager) manager));
    }
}
