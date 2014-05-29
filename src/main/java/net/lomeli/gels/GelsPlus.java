package net.lomeli.gels;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;

import net.minecraftforge.common.config.Configuration;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.core.EnchantShield;
import net.lomeli.gels.core.GPTab;
import net.lomeli.gels.core.IProxy;
import net.lomeli.gels.core.Recipes;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.entity.EntityGelThrowable;
import net.lomeli.gels.gel.GelPropulsion;
import net.lomeli.gels.gel.GelRepulsion;
import net.lomeli.gels.item.ModItems;
import net.lomeli.gels.network.GPChannel;
import net.lomeli.gels.network.PacketClearList;
import net.lomeli.gels.network.PacketUpdateClient;
import net.lomeli.gels.network.PacketUpdateRegistry;

import net.lomeli.lomlib.util.EnchantmentUtil;
import net.lomeli.lomlib.util.LogHelper;
import net.lomeli.lomlib.util.UpdateHelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Strings.MODID, name = Strings.NAME, version = Strings.VERSION, dependencies = "required-after:LomLib;")
public class GelsPlus {
    @Mod.Instance(Strings.MODID)
    public static GelsPlus instance;

    @SidedProxy(clientSide = Strings.CLIENT, serverSide = Strings.COMMON)
    public static IProxy proxy;

    public static LogHelper logger = new LogHelper(Strings.NAME);

    public static UpdateHelper updater = new UpdateHelper();

    public static CreativeTabs modTab = new GPTab();

    public static boolean debugMode, allowThrowable, check, gelEffects, enableCT, checked = false;
    public static int ticksBetweenThrow;

    public static GPChannel packetChannel;

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStart(event);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        debugMode = config.get("Options", "debugBoots", false, Strings.debugBootInfo).getBoolean(false);
        allowThrowable = config.get("Options", "allowThrowable", true, Strings.allowThrowableInfo).getBoolean(true);
        check = config.get("Options", "checkForUpdates", true, Strings.updateInfo).getBoolean(true);
        gelEffects = config.get("Options", "gelEffects", true, Strings.effectInfo).getBoolean(true);
        ticksBetweenThrow = config.get("Options", "ticksBetweenThrow", 60, Strings.dispenserTick).getInt(60);
        enableCT = config.get("Options", "connectedTexturesEnabled", true, Strings.enableCT).getBoolean(true);

        GelRepulsion.bounciness = config.get("Gel_Config", "repGel_bounciness", 1.7).getDouble(1.7);
        GelPropulsion.speedBoost = config.get("Gel_Config", "proGel_speedBoost", 0.09).getDouble(0.09);

        int j = EnchantmentUtil.getUniqueEnchantID();
        EnchantShield.enchantID = config.get("Options", "gelShieldEnchantID", j).getInt(j);

        configureBlackList(config);

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
        packetChannel = new GPChannel(PacketUpdateRegistry.class, PacketUpdateClient.class, PacketClearList.class);
        proxy.registerTiles();
        proxy.registerRenders();
        proxy.registerEvents();
        EntityGelThrowable.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        packetChannel.postInitialise();
        proxy.getRegistry().initRegistry();
        GelAbility.gelRegistry = proxy.getRegistry();
        Recipes.loadRecipes();
    }

    @SuppressWarnings("unchecked")
    private void configureBlackList(Configuration config) {
        String list = config.get("Options", "entityBlackList", "", Strings.blackList).getString();
        if (!list.isEmpty()) {
            String[] classes = list.split(";");
            for (String clazz : classes) {
                if (!clazz.isEmpty()) {
                    try {
                        Class<?> entityClass = Class.forName(clazz);
                        if (entityClass != null && entityClass.isAssignableFrom(Entity.class)) {
                            proxy.getRegistry().addClassToBlackList((Class<? extends Entity>) entityClass);
                            logger.logInfo(clazz + " has been added to the black list.");
                        }
                    }catch (Exception e) {
                        logger.logError(clazz + " could not be added to the blackList!");
                    }
                }
            }
        }
    }

}
