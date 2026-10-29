package br.com.endlessmutationcailee.item;

import br.com.endlessmutationcailee.EndlessMutationCailee;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {
    public static Item caileeSummoner;

    private ModItems() {}

    public static void register() {
        caileeSummoner = new ItemCaileeSummoner().setUnlocalizedName("cailee_summoner").setTextureName(EndlessMutationCailee.MODID + ":cailee_summoner");
        GameRegistry.registerItem(caileeSummoner, "cailee_summoner");
    }
}
