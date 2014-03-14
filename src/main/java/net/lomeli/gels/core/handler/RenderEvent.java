package net.lomeli.gels.core.handler;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.ForgeSubscribe;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.gel.GelRegistry;

public class RenderEvent {

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void entityPreRender(RenderLivingEvent.Pre event) {
        if (GelRegistry.INSTANCE().coloredList().containsKey(event.entity.entityId)) {
            GelAbility gel = GelRegistry.INSTANCE().getGel(GelRegistry.INSTANCE().coloredList().get(event.entity.entityId));
            if (gel != null) {
                applyColor(gel.gelColor());
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void entityPostRender(RenderLivingEvent.Post event) {
        if (GelRegistry.INSTANCE().coloredList().containsKey(event.entity.entityId)) {
            resetColor();
        }
    }

    public static void applyColor(Color color) {
        applyColor(color, 1);
    }

    public static void applyColor(Color color, float alpha) {
        float r = (color.getRed() / 255f), g = (color.getGreen() / 255f), b = (color.getBlue() / 255f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(r, g, b, alpha);
    }

    public static void resetColor() {
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}
