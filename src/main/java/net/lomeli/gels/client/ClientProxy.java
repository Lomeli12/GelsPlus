package net.lomeli.gels.client;

import net.lomeli.gels.client.entity.RenderGel;
import net.lomeli.gels.core.IProxy;
import net.lomeli.gels.entity.EntityAdhesiveGel;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy{

    @Override
    public void registerTiles() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityAdhesiveGel.class, new RenderGel(0, 190 / 255f, 25 / 255F));
    }

}
