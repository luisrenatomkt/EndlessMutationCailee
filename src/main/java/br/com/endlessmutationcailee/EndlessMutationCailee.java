package br.com.endlessmutationcailee;

import br.com.endlessmutationcailee.config.ModConfig;
import br.com.endlessmutationcailee.entity.EntityCailee;
import br.com.endlessmutationcailee.item.ModItems;
import br.com.endlessmutationcailee.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = EndlessMutationCailee.MODID, name = EndlessMutationCailee.NAME, version = EndlessMutationCailee.VERSION, acceptedMinecraftVersions = "[1.8.9]")
public class EndlessMutationCailee {
    public static final String MODID = "endlessmutationcailee";
    public static final String NAME = "Endless Mutation: Cailee";
    public static final String VERSION = "1.0.0";

    @SidedProxy(clientSide = "br.com.endlessmutationcailee.proxy.ClientProxy", serverSide = "br.com.endlessmutationcailee.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModConfig.init(event.getSuggestedConfigurationFile());
        ModItems.register();
        ModEntities.register();
        MinecraftForge.EVENT_BUS.register(new br.com.endlessmutationcailee.event.ImmortalityHandler());
        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
