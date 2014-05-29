package net.lomeli.gels.core;

import net.lomeli.gels.gel.GelRegistry;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

public interface IProxy {
    public void serverStart(FMLServerStartingEvent event);

    public void registerTiles();

    public void registerRenders();

    public void registerEvents();

    public GelRegistry getRegistry();
}
