package net.lomeli.gels.client;

import net.minecraft.util.IIcon;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.core.IProxy;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.entity.EntityGelThrowable;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ClientProxy implements IProxy {

    @Override
    public void registerTiles() {
        // TODO Auto-generated method stub

    }

    @Override
    public void registerRenders() {
        ModBlocks.gelRenderID = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerBlockHandler(new RenderGels());
        RenderingRegistry.registerEntityRenderingHandler(EntityGelThrowable.class, new RenderGelThrowable());
    }

    @Override
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static IIcon gel;
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() != 0 && event.map.getTextureType() == 1) {
            gel = event.map.registerIcon(Strings.MODID.toLowerCase() + ":gelBlob");
        }
    }

}
