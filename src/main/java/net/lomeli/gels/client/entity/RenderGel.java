package net.lomeli.gels.client.entity;

import org.lwjgl.opengl.GL11;

import net.lomeli.gels.core.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderGel extends Render {
    private static final ResourceLocation gel = new ResourceLocation(Strings.MODID.toLowerCase(), "textures/entities/gel.png");
    private float r, g, b;

    public RenderGel() {
        r = 1f;
        g = 1f;
        b = 1f;
    }

    public RenderGel(float red, float green, float blue) {
        r = red;
        g = green;
        b = blue;
    }

    public void renderGel(EntityHanging paramEntity, double paramDouble1, double paramDouble2, double paramDouble3,
            float paramFloat1, float paramFloat2) {
        GL11.glPushMatrix();
        GL11.glTranslated(paramDouble1, paramDouble2, paramDouble3);
        GL11.glRotatef(paramFloat1, 0.0F, 1.0F, 0.0F);
        GL11.glEnable(32826);

        GL11.glColor3f(r, g, b);

        this.bindTexture(gel);

        float f = 0.0625F;
        GL11.glScalef(f, f, f);
        func_77010_a(paramEntity, 16, 16, 1, 1);

        GL11.glColor3f(1f, 1f, 1f);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    private void func_77010_a(EntityHanging p_77010_1_, int p_77010_2_, int p_77010_3_, int p_77010_4_, int p_77010_5_) {
        float f1 = -p_77010_2_ / 2.0F;
        float f2 = -p_77010_3_ / 2.0F;

        float f3 = 0.5F;

        float f4 = 0.75F;
        float f5 = 0.8125F;
        float f6 = 0.0F;
        float f7 = 0.0625F;

        float f8 = 0.75F;
        float f9 = 0.8125F;
        float f10 = 0.00195313F;
        float f11 = 0.00195313F;

        float f12 = 0.7519531F;
        float f13 = 0.7519531F;
        float f14 = 0.0F;
        float f15 = 0.0625F;

        for(int i = 0; i < p_77010_2_ / 16; i++)
            for(int j = 0; j < p_77010_3_ / 16; j++) {
                float f16 = f1 + (i + 1) * 16;
                float f17 = f1 + i * 16;
                float f18 = f2 + (j + 1) * 16;
                float f19 = f2 + j * 16;

                func_77008_a(p_77010_1_, (f16 + f17) / 2.0F, (f18 + f19) / 2.0F);

                float f20 = (p_77010_4_ + p_77010_2_ - i * 16) / 256.0F;
                float f21 = (p_77010_4_ + p_77010_2_ - (i + 1) * 16) / 256.0F;
                float f22 = (p_77010_5_ + p_77010_3_ - j * 16) / 256.0F;
                float f23 = (p_77010_5_ + p_77010_3_ - (j + 1) * 16) / 256.0F;

                Tessellator localTessellator = Tessellator.instance;
                localTessellator.startDrawingQuads();
                localTessellator.setNormal(0.0F, 0.0F, -1.0F);
                localTessellator.addVertexWithUV(f16, f19, -f3, f21, f22);
                localTessellator.addVertexWithUV(f17, f19, -f3, f20, f22);
                localTessellator.addVertexWithUV(f17, f18, -f3, f20, f23);
                localTessellator.addVertexWithUV(f16, f18, -f3, f21, f23);

                localTessellator.setNormal(0.0F, 0.0F, 1.0F);
                localTessellator.addVertexWithUV(f16, f18, f3, f4, f6);
                localTessellator.addVertexWithUV(f17, f18, f3, f5, f6);
                localTessellator.addVertexWithUV(f17, f19, f3, f5, f7);
                localTessellator.addVertexWithUV(f16, f19, f3, f4, f7);

                localTessellator.setNormal(0.0F, 1.0F, 0.0F);
                localTessellator.addVertexWithUV(f16, f18, -f3, f8, f10);
                localTessellator.addVertexWithUV(f17, f18, -f3, f9, f10);
                localTessellator.addVertexWithUV(f17, f18, f3, f9, f11);
                localTessellator.addVertexWithUV(f16, f18, f3, f8, f11);

                localTessellator.setNormal(0.0F, -1.0F, 0.0F);
                localTessellator.addVertexWithUV(f16, f19, f3, f8, f10);
                localTessellator.addVertexWithUV(f17, f19, f3, f9, f10);
                localTessellator.addVertexWithUV(f17, f19, -f3, f9, f11);
                localTessellator.addVertexWithUV(f16, f19, -f3, f8, f11);

                localTessellator.setNormal(-1.0F, 0.0F, 0.0F);
                localTessellator.addVertexWithUV(f16, f18, f3, f13, f14);
                localTessellator.addVertexWithUV(f16, f19, f3, f13, f15);
                localTessellator.addVertexWithUV(f16, f19, -f3, f12, f15);
                localTessellator.addVertexWithUV(f16, f18, -f3, f12, f14);

                localTessellator.setNormal(1.0F, 0.0F, 0.0F);
                localTessellator.addVertexWithUV(f17, f18, -f3, f13, f14);
                localTessellator.addVertexWithUV(f17, f19, -f3, f13, f15);
                localTessellator.addVertexWithUV(f17, f19, f3, f12, f15);
                localTessellator.addVertexWithUV(f17, f18, f3, f12, f14);
                localTessellator.draw();
            }
    }

    private void func_77008_a(EntityHanging p_77008_1_, float p_77008_2_, float p_77008_3_) {
        int i = MathHelper.floor_double(p_77008_1_.posX);
        int j = MathHelper.floor_double(p_77008_1_.posY + p_77008_3_ / 16.0F);
        int k = MathHelper.floor_double(p_77008_1_.posZ);
        if(p_77008_1_.hangingDirection == 2)
            i = MathHelper.floor_double(p_77008_1_.posX + p_77008_2_ / 16.0F);
        if(p_77008_1_.hangingDirection == 1)
            k = MathHelper.floor_double(p_77008_1_.posZ - p_77008_2_ / 16.0F);
        if(p_77008_1_.hangingDirection == 0)
            i = MathHelper.floor_double(p_77008_1_.posX - p_77008_2_ / 16.0F);
        if(p_77008_1_.hangingDirection == 3)
            k = MathHelper.floor_double(p_77008_1_.posZ + p_77008_2_ / 16.0F);
        int m = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int n = m % 65536;
        int i1 = m / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, n, i1);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity paramEntity) {
        return gel;
    }

    @Override
    public void doRender(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1,
            float paramFloat2) {
        renderGel((EntityHanging) paramEntity, paramDouble1, paramDouble2, paramDouble3, paramFloat1, paramFloat2);
    }

}
