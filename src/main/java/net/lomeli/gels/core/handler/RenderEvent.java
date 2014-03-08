package net.lomeli.gels.core.handler;

import java.lang.reflect.Field;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fluids.FluidRegistry;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.gel.GelRegistry;

import net.lomeli.lomlib.client.render.RenderUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderEvent {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void entityPreRender(RenderLivingEvent.Pre event) {
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
    public void entityPostRender(RenderLivingEvent.Post event) {
        if (GelRegistry.getInstance().coloredList().containsKey(event.entity.getEntityId())) {
            RenderUtils.resetColor();
        }
    }
}
