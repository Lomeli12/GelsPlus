package net.lomeli.gels.core;

import net.lomeli.gels.gel.GelRegistry;

public interface IProxy {
    public void registerTiles();

    public void registerRenders();

    public void registerEvents();

    public GelRegistry getRegistry();
}
