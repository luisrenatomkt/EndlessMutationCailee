package br.com.endlessmutationcailee.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {
    public static Item caileeSummoner;

    private ModItems() {}

    public static void register() {
        caileeSummoner = new ItemCaileeSummoner();
        caileeSummoner.setUnlocalizedName("cailee_summoner");
        GameRegistry.registerItem(caileeSummoner, "cailee_summoner");
    }
}
