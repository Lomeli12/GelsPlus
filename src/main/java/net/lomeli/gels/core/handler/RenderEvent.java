package net.lomeli.gels.core.handler;

import net.minecraftforge.client.event.RenderLivingEvent;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.network.PacketUpdateRegistry;

import net.lomeli.lomlib.client.render.RenderUtils;
import net.lomeli.lomlib.network.PacketHandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderEvent {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityPreRender(RenderLivingEvent.Pre event) {
        if (GelsPlus.proxy.getRegistry().coloredList().containsKey(event.entity.getEntityId())) {
            GelAbility gel = null;
            try {
                gel = GelsPlus.proxy.getRegistry().getGel(GelsPlus.proxy.getRegistry().coloredList().get(event.entity.getEntityId())).newInstance();
            }catch (Exception e) {
                e.printStackTrace();
                PacketHandler.sendEverywhere(GelsPlus.packetChannel.getChannel(), new PacketUpdateRegistry(event.entity));
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
