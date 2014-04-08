package net.lomeli.gels.core.handler;

import net.minecraftforge.client.event.RenderLivingEvent;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;

import net.lomeli.lomlib.client.render.RenderUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderEvent {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityPreRender(RenderLivingEvent.Pre event) {
        if (GelsPlus.proxy.getRegistry().coloredList().containsKey(event.entity.getEntityId())) {
            GelAbility gel = GelsPlus.proxy.getRegistry().getGel(event.entity.getEntityData().getInteger("gelEffect"));
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
