package br.com.endlessmutationcailee.client;

import br.com.endlessmutationcailee.EndlessMutationCailee;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import br.com.endlessmutationcailee.entity.EntityCailee;

public class RenderCailee extends RenderLiving<EntityCailee> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(EndlessMutationCailee.MODID, "textures/entity/cailee.png");

    public RenderCailee(RenderManager manager) {
        super(manager, new ModelCailee(), 0.45F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCailee entity) {
        return TEXTURE;
    }

    @Override
    protected void preRenderCallback(EntityCailee entity, float partialTickTime) {
        float s = (float)Math.max(1.0D, Math.min(6.0D, entity.getVisualScale()));
        this.shadowSize = Math.min(3.0F, 0.45F * s);
        net.minecraft.client.renderer.GlStateManager.scale(s, s, s);
    }
}
