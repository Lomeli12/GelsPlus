package net.lomeli.gels.client;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.lomlib.entity.EntityUtil;
import net.lomeli.lomlib.util.ToolTipUtil;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.core.Proxy;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.core.handler.RenderEvent;
import net.lomeli.gels.entity.EntityGelThrowable;

public class ClientProxy extends Proxy {

    @Override
    public void registerTiles() {
        super.registerTiles();
    }

    @Override
    public void registerRenders() {
        super.registerRenders();
        ModBlocks.gelRenderID = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(new RenderGels());
        RenderingRegistry.registerEntityRenderingHandler(EntityGelThrowable.class, new RenderGelThrowable());
    }

    @Override
    public void registerEvents() {
        super.registerEvents();
        MinecraftForge.EVENT_BUS.register(new RenderEvent());
    }
}
