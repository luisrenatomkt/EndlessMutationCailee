package br.com.endlessmutationcailee.proxy;

import br.com.endlessmutationcailee.client.RenderCailee;
import br.com.endlessmutationcailee.entity.EntityCailee;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityCailee.class, manager ->
            new RenderCailee((RenderManager) manager));
    }
}
