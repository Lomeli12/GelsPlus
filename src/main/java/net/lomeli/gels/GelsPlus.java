package net.lomeli.gels;

import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.lomeli.lomlib.util.UpdateHelper;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.core.GPTab;
import net.lomeli.gels.core.IProxy;
import net.lomeli.gels.core.Recipes;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.entity.EntityGelThrowable;
import net.lomeli.gels.gel.GelRegistry;
import net.lomeli.gels.item.ModItems;
import net.lomeli.gels.network.GPChannel;
import net.lomeli.gels.network.PacketNBT;

@Mod(modid = Strings.MODID, name = Strings.NAME, version = Strings.VERSION, dependencies = "required-after:LomLibCore;")
public class GelsPlus {
    @Mod.Instance(Strings.MODID)
    public static GelsPlus instance;

    @SidedProxy(clientSide = Strings.CLIENT, serverSide = Strings.COMMON)
    public static IProxy proxy;

    public static UpdateHelper updater = new UpdateHelper();

    public static CreativeTabs modTab = new GPTab();

    public static boolean debugMode, allowThrowable, check, checked = false;

    public static GPChannel packetChannel;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        debugMode = config.get("Options", "debugBoots", false, Strings.debugBootInfo).getBoolean(false);
        allowThrowable = config.get("Options", "allowThrowable", true, Strings.allowThrowableInfo).getBoolean(true);
        check = config.get("Options", "checkForUpdates", true, Strings.updateInfo).getBoolean(true);

        config.save();

        if (check) {
            try {
                updater.check(Strings.NAME, Strings.XML, Strings.MAJOR, Strings.MINOR, Strings.REVISION);
            } catch (Exception e) {
            }
        }

        ModBlocks.loadBlocks();
        ModItems.loadItems();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        packetChannel = new GPChannel(PacketNBT.class);
        proxy.registerTiles();
        proxy.registerRenders();
        proxy.registerEvents();
        EntityGelThrowable.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        packetChannel.postInitialise();
        GelAbility.gelRegistry = GelRegistry.getInstance();
        Recipes.loadRecipes();
    }

}
