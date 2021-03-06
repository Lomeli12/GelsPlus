package net.lomeli.gels.client;

import org.lwjgl.opengl.GL11;

import java.awt.Color;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidRegistry;

import net.lomeli.lomlib.client.render.RenderUtils;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.entity.EntityGelThrowable;
import net.lomeli.gels.item.ModItems;

public class RenderGelThrowable extends Render {

    @Override
    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        doRenderGel((EntityGelThrowable) entity, d0, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity paramEntity) {
        return RenderUtils.ITEM_TEXTURE;
    }

    public void doRenderGel(EntityGelThrowable entity, double d0, double d1, double d2, float f, float f1) {
        if (entity.getSyncBlock() > -1) {

            GL11.glPushMatrix();

            GL11.glTranslatef((float) d0, (float) d1, (float) d2);
            GL11.glEnable(32826);
            GL11.glScalef(0.5F, 0.5F, 0.5F);

            GelAbility gel = null;
            try {
                gel = GelsPlus.proxy.getRegistry().getGel(entity.getSyncBlock()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Color color = gel != null ? gel.gelColor() : Color.WHITE;
            if (color == null)
                color = Color.WHITE;

            RenderUtils.applyColor(color);

            bindEntityTexture(entity);

            renderIcon(Tessellator.instance, ModItems.gelBlob.getIconFromDamage(0));

            RenderUtils.resetColor();

            GL11.glDisable(32826);
            GL11.glPopMatrix();
        }
    }

    private void renderIcon(Tessellator tessellator, IIcon icon) {
        if (icon == null)
            icon = FluidRegistry.WATER.getIcon();

        float f1 = icon.getMinU();
        float f2 = icon.getMaxU();
        float f3 = icon.getMinV();
        float f4 = icon.getMaxV();
        float f5 = 1.0F;
        float f6 = 0.5F;
        float f7 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f6, 0.0F - f7, 0.0D, f1, f4);
        tessellator.addVertexWithUV(f5 - f6, 0.0F - f7, 0.0D, f2, f4);
        tessellator.addVertexWithUV(f5 - f6, f5 - f7, 0.0D, f2, f3);
        tessellator.addVertexWithUV(0.0F - f6, f5 - f7, 0.0D, f1, f3);
        tessellator.draw();
    }
}
