package net.lomeli.gels.core;

import cpw.mods.fml.common.event.FMLServerStartingEvent;

import net.lomeli.gels.gel.GelRegistry;

public interface IProxy {
    public void serverStart(FMLServerStartingEvent event);

    public void registerTiles();

    public void registerRenders();

    public void registerEvents();

    public GelRegistry getRegistry();
}
