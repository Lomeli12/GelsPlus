package net.lomeli.gels.core.handler;

import net.minecraftforge.client.event.RenderLivingEvent;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.gel.GelRegistry;

import net.lomeli.lomlib.client.render.RenderUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderEvent {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityColorPreRender(RenderLivingEvent.Pre event) {
        if (GelRegistry.getInstance().coloredList().containsKey(event.entity.getEntityId())) {
            GelAbility gel = GelRegistry.getInstance().getGel(
                    GelRegistry.getInstance().coloredList().get(event.entity.getEntityId()));
            if (gel != null) {
                RenderUtils.applyColor(gel.gelColor());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityColorPostRender(RenderLivingEvent.Post event) {
        if (GelRegistry.getInstance().coloredList().containsKey(event.entity.getEntityId())) {
            RenderUtils.resetColor();
        }
    }
}
