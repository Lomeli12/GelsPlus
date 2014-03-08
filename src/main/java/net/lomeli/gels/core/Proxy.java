package net.lomeli.gels.core;

import net.lomeli.gels.block.TileGel;

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
        // TODO Auto-generated method stub

    }

}
