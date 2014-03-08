package net.lomeli.gels.client;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.util.ChatComponentText;

import net.minecraftforge.common.MinecraftForge;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.core.Proxy;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.entity.EntityGelThrowable;

import net.lomeli.lomlib.util.ToolTipUtil;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void clientTickEvent(PlayerTickEvent event) {
        if (!GelsPlus.checked && !GelsPlus.updater.isUpdate()) {
            if (event.phase == Phase.END) {
                if (FMLClientHandler.instance().getClient().currentScreen == null) {
                    GuiIngame ui = FMLClientHandler.instance().getClient().ingameGUI;
                    if (ui != null) {
                        ui.getChatGUI().printChatMessage(
                                new ChatComponentText(ToolTipUtil.BLUE + "[" + ToolTipUtil.ORANGE + Strings.NAME
                                        + ToolTipUtil.BLUE + "]: There is a new version available at "
                                        + GelsPlus.updater.getDownloadURL()));
                        GelsPlus.checked = true;
                    }
                }
            }
        }
    }
}
