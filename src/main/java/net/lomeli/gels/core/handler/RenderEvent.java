package net.lomeli.gels.core.handler;

import net.minecraftforge.client.event.RenderLivingEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.lomlib.client.render.RenderUtils;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.network.PacketUpdateRegistry;

public class RenderEvent {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityPreRender(RenderLivingEvent.Pre event) {
        if (GelsPlus.proxy.getRegistry().coloredList().containsKey(event.entity.getEntityId())) {
            GelAbility gel = null;
            try {
                gel = GelsPlus.proxy.getRegistry().getGel(GelsPlus.proxy.getRegistry().coloredList().get(event.entity.getEntityId())).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                GelsPlus.pktHandler.sendEverywhere(new PacketUpdateRegistry(event.entity));
                return;
            }
            if (gel != null)
                RenderUtils.applyColor(gel.gelColor());
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityPostRender(RenderLivingEvent.Post event) {
        if (GelsPlus.proxy.getRegistry().coloredList().containsKey(event.entity.getEntityId()))
            RenderUtils.resetColor();
    }

}
