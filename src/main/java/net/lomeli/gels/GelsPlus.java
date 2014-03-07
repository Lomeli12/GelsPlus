package net.lomeli.gels;

import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.common.config.Configuration;

import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.core.GPTab;
import net.lomeli.gels.core.IProxy;
import net.lomeli.gels.core.Recipes;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.entity.EntityGelThrowable;
import net.lomeli.gels.item.ModItems;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Strings.MODID, name = Strings.NAME, version = Strings.VERSION)
public class GelsPlus {
    @Mod.Instance(Strings.MODID)
    public static GelsPlus instance;

    @SidedProxy(clientSide = "net.lomeli.gels.client.ClientProxy", serverSide = "net.lomeli.gels.core.Proxy")
    public static IProxy proxy;

    public static CreativeTabs modTab = new GPTab();

    public static boolean debugMode;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        debugMode = config.get("Options", "debugBoots", false, Strings.debugBootInfo).getBoolean(false);

        config.save();

        ModBlocks.loadBlocks();
        ModItems.loadItems();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.registerRenders();
        EntityGelThrowable.init();
        Recipes.loadRecipes();
    }

}
