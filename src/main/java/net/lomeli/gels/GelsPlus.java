package net.lomeli.gels;

import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.core.GPTab;
import net.lomeli.gels.core.IProxy;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.item.ModItems;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.common.Configuration;

@Mod(modid = Strings.MODID, name = Strings.NAME, version = Strings.VERSION)
public class GelsPlus {
    @Mod.Instance(Strings.MODID)
    public static GelsPlus instance;
    
    public static int proGelID, repGelID, gelBucketID, longFallID;

    @SidedProxy(clientSide = "net.lomeli.gels.client.ClientProxy", serverSide = "net.lomeli.gels.core.Proxy")
    public static IProxy proxy;

    public static CreativeTabs modTab = new GPTab();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        
        config.load();
        
        proGelID = config.getBlock("proGel", 400).getInt(400);
        repGelID = config.getBlock("repGel", 401).getInt(401);
        
        gelBucketID = config.getItem("gelBucket", 4000).getInt(4000);
        longFallID = config.getItem("longFall", 4001).getInt(4001);
        
        config.save();
        
        ModBlocks.loadBlocks();
        ModItems.loadItems();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.registerRenders();
    }

}
