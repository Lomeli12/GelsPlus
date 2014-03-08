package net.lomeli.gels.core;

import net.minecraftforge.common.MinecraftForge;

import net.lomeli.gels.block.TileGel;
import net.lomeli.gels.core.handler.GelEffectHandler;

import cpw.mods.fml.common.registry.GameRegistry;

public class Proxy implements IProxy {

    @Override
    public void registerTiles() {
        GameRegistry.registerTileEntity(TileGel.class, "net.lomeli.gels.block.tileGel");
    }

    @Override
    public void registerRenders() {
        
    }

    @Override
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new GelEffectHandler());
    }

}
