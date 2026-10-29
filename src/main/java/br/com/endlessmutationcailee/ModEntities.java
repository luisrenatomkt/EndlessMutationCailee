package br.com.endlessmutationcailee;

import br.com.endlessmutationcailee.entity.EntityCailee;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public final class ModEntities {
    private ModEntities() {}

    public static void register() {
        EntityRegistry.registerModEntity(EntityCailee.class, "Cailee", 1, EndlessMutationCailee.MODID, 64, 3, true, 0xE91E8C, 0xF7A8CE);
    }
}
