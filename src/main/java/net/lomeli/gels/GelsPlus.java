package net.lomeli.gels;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;

import net.minecraftforge.common.config.Configuration;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.lomlib.network.PacketHandler;
import net.lomeli.lomlib.util.EnchantmentUtil;
import net.lomeli.lomlib.util.LogHelper;
import net.lomeli.lomlib.util.UpdateHelper;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.core.*;
import net.lomeli.gels.entity.EntityGelThrowable;
import net.lomeli.gels.gel.GelPropulsion;
import net.lomeli.gels.gel.GelRepulsion;
import net.lomeli.gels.item.ModItems;
import net.lomeli.gels.network.PacketClearList;
import net.lomeli.gels.network.PacketUpdateClient;
import net.lomeli.gels.network.PacketUpdateRegistry;

@Mod(modid = Strings.MODID, name = Strings.NAME, version = Strings.VERSION, dependencies = "required-after:LomLib;")
public class GelsPlus {
    @Mod.Instance(Strings.MODID)
    public static GelsPlus instance;

    @SidedProxy(clientSide = Strings.CLIENT, serverSide = Strings.COMMON)
    public static IProxy proxy;

    public static LogHelper logger = new LogHelper(Strings.NAME);

    public static UpdateHelper updater = new UpdateHelper(Strings.NAME, Strings.MODID);

    public static CreativeTabs modTab = new GPTab();

    public static boolean allowThrowable, check, gelEffects, enableCT, checked = false;
    public static int ticksBetweenThrow;

    public static PacketHandler pktHandler;

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStart(event);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());

        config.load();

        allowThrowable = config.get("Options", "allowThrowable", true, Strings.allowThrowableInfo).getBoolean(true);
        check = config.get("Options", "checkForUpdates", true, Strings.updateInfo).getBoolean(true);
        gelEffects = config.get("Options", "gelEffects", true, Strings.effectInfo).getBoolean(true);
        ticksBetweenThrow = config.get("Options", "ticksBetweenThrow", 60, Strings.dispenserTick).getInt(60);
        enableCT = config.get("Options", "connectedTexturesEnabled", true, Strings.enableCT).getBoolean(true);

        GelRepulsion.bounciness = config.get("Gel_Config", "repGel_bounciness", 1.25).getDouble(1.25);
        GelPropulsion.speedBoost = config.get("Gel_Config", "proGel_speedBoost", 0.09).getDouble(0.09);

        int j = EnchantmentUtil.getUniqueEnchantID();
        EnchantShield.enchantID = config.get("Options", "gelShieldEnchantID", j).getInt(j);

        configureBlackList(config);

        config.save();

        if (check) {
            try {
                updater.check(Strings.XML, Strings.MAJOR, Strings.MINOR, Strings.REVISION);
            } catch (Exception e) {
            }
        }

        ModBlocks.loadBlocks();
        ModItems.loadItems();
    }

    @SuppressWarnings("unchecked")
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        pktHandler = new PacketHandler(Strings.MODID, PacketUpdateRegistry.class, PacketUpdateClient.class, PacketClearList.class);
        proxy.registerTiles();
        proxy.registerRenders();
        proxy.registerEvents();
        EntityGelThrowable.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.getRegistry().initRegistry();
        GelAbility.gelRegistry = proxy.getRegistry();
        Recipes.loadRecipes();
    }

    @SuppressWarnings("unchecked")
    private void configureBlackList(Configuration config) {
        String list = config.getString("entityBlackList", "Options", "", Strings.blackList);
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
                    } catch (Exception e) {
                        logger.logError(clazz + " could not be added to the blackList!");
                    }
                }
            }
        }
        list = config.getString("blockBlackList", "Options", "", Strings.blockList);
        if (!list.isEmpty()) {
            String[] blocks = list.split("|");
            for (String name : blocks) {
                if (!name.isEmpty()) {
                    String[] id = name.split(":");
                    if (id != null && id.length == 2) {
                        Block block = GameRegistry.findBlock(id[0], id[1]);
                        if (block != null)
                            proxy.getRegistry().addBlockToBlackList(block);
                    }
                }
            }
        }
    }

}
