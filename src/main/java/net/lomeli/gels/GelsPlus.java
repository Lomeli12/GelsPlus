package net.lomeli.gels;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.common.Configuration;

import net.lomeli.lomlib.util.UpdateHelper;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.core.GPTab;
import net.lomeli.gels.core.IProxy;
import net.lomeli.gels.core.ModIds;
import net.lomeli.gels.core.Recipes;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.entity.EntityGelThrowable;
import net.lomeli.gels.gel.GelRegistry;
import net.lomeli.gels.item.ModItems;
import net.lomeli.gels.network.GPPacketHandler;

@Mod(modid = Strings.MODID, name = Strings.NAME, version = Strings.VERSION, dependencies = "required-after:LomLib;")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, channels = { Strings.PACKET }, packetHandler = GPPacketHandler.class)
public class GelsPlus {
    @Mod.Instance(Strings.MODID)
    public static GelsPlus instance;

    @SidedProxy(clientSide = Strings.CLIENT, serverSide = Strings.COMMON)
    public static IProxy proxy;

    public static UpdateHelper updater = new UpdateHelper();

    public static CreativeTabs modTab = new GPTab();

    public static boolean debugMode, allowThrowable, check, gelEffects, checked = false;
    public static int ticksBetweenThrow;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        String opt = "Options";

        debugMode = config.get(opt, "debugBoots", false, Strings.debugBootInfo).getBoolean(false);
        allowThrowable = config.get(opt, "allowThrowable", true, Strings.allowThrowableInfo).getBoolean(true);
        check = config.get(opt, "checkForUpdates", true, Strings.updateInfo).getBoolean(true);
        gelEffects = config.get(opt, "gelEffects", true, Strings.effectInfo).getBoolean(true);
        ticksBetweenThrow = config.get("Options", "ticksBetweenThrow", 60, Strings.dispenserTick).getInt(60);

        // ModIds
        ModIds.gelBlock = config.getBlock("gelBlock", 3010).getInt(3010);
        ModIds.gelDispenser = config.getBlock("gelDispenser", 3011).getInt(3011);
        ModIds.blob = config.getItem("dullBlob", 3840).getInt(3840);
        ModIds.gelBucket = config.getItem("gelBucket", 3841).getInt(3841);
        ModIds.gelBlob = config.getItem("gelBlob", 3842).getInt(3842);
        ModIds.longFallBoots = config.getItem("debugBootID", 3843).getInt(3843);

        config.save();

        if (check) {
            try {
                updater.check(Strings.NAME, Strings.XML, Strings.MAJOR, Strings.MINOR, Strings.REVISION);
            }catch (Exception e) {
            }
        }

        ModBlocks.loadBlocks();
        ModItems.loadItems();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerTiles();
        proxy.registerRenders();
        proxy.registerEvents();
        EntityGelThrowable.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        GelAbility.gelRegistry = GelRegistry.INSTANCE();
        Recipes.loadRecipes();
    }

}
