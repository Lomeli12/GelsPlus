package net.lomeli.gels.client;

import net.minecraft.item.Item;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileDispenser;
import net.lomeli.gels.core.Proxy;
import net.lomeli.gels.core.handler.RenderEvent;
import net.lomeli.gels.entity.EntityGelThrowable;

public class ClientProxy extends Proxy {

    @Override
    public void registerTiles() {
        super.registerTiles();
        ClientRegistry.bindTileEntitySpecialRenderer(TileDispenser.class, new RenderDispenser());
    }

    @Override
    public void registerRenders() {
        super.registerRenders();
        ModBlocks.dispenserRenderID = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerEntityRenderingHandler(EntityGelThrowable.class, new RenderGelThrowable());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.gelDispenser), new RenderDispenser());
    }

    @Override
    public void registerEvents() {
        super.registerEvents();
        MinecraftForge.EVENT_BUS.register(new RenderEvent());
    }
}
