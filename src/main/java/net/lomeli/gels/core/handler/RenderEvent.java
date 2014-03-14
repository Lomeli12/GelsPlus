package net.lomeli.gels.core.handler;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.lomlib.client.render.RenderUtils;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.gel.GelRegistry;

public class RenderEvent {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityPreRender(RenderLivingEvent.Pre event) {
        if (GelRegistry.INSTANCE().coloredList().containsKey(event.entity.getEntityId())) {
            GelAbility gel = GelRegistry.INSTANCE().getGel(
                    GelRegistry.INSTANCE().coloredList().get(event.entity.getEntityId()));
            if (gel != null) {
                RenderUtils.applyColor(gel.gelColor());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityPostRender(RenderLivingEvent.Post event) {
        if (GelRegistry.INSTANCE().coloredList().containsKey(event.entity.getEntityId())) {
            RenderUtils.resetColor();
        }
    }

    public void playerCrossHair(RenderGameOverlayEvent.Pre event){
        
    }
}
