package net.lomeli.gels.core;

import net.minecraftforge.common.MinecraftForge;

import net.lomeli.gels.block.TileDispenser;
import net.lomeli.gels.block.TileGel;
import net.lomeli.gels.core.handler.EventHandler;
import net.lomeli.gels.gel.GelRegistry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class Proxy implements IProxy {

    private GelRegistry instance;

    @Override
    public void registerTiles() {
        GameRegistry.registerTileEntity(TileGel.class, "net.lomeli.gels.block.tileGel");
        GameRegistry.registerTileEntity(TileDispenser.class, "net.lomeli.gels.block.tileDispenser");
    }

    @Override
    public void registerRenders() {
    }

    @Override
    public void registerEvents() {
        instance = new GelRegistry();
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLCommonHandler.instance().bus().register(new EventHandler.FMLEvents());
    }

    @Override
    public GelRegistry getRegistry() {
        return instance;
    }

}
