package net.lomeli.gels.client;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.gel.BlockGel;
import net.lomeli.gels.core.GelRegistry;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderGels implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int meta, int model, RenderBlocks renderer) {
        Color blockColor = GelRegistry.getInstance().getColorByBlock(block);

        float r = (blockColor.getRed() / 255f), g = (blockColor.getGreen() / 255f), b = (blockColor.getBlue() / 255f);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(r, g, b, 1);
        renderer.setRenderBounds(0F, 0F, 0F, 1F, 0.01F, 1F);
        drawBlockFaces(renderer, block, block.getIcon(meta, 0));
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int meta, RenderBlocks renderer) {
        ((BlockGel) block).bounds(meta);

        Color blockColor = GelRegistry.getInstance().getColorByBlock(world.getBlock(x, y, z));

        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorOpaque_I(blockColor.getRGB());
        renderBlock(world, x, y, z, block, renderer, ((BlockGel) block).getIcon(0, 0));

        renderer.clearOverrideBlockTexture();
        block.setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
        renderer.setRenderBoundsFromBlock(block);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int paramInt) {
        return true;
    }

    @Override
    public int getRenderId() {
        return ModBlocks.gelRenderID;
    }

    private static void renderBlock(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer, IIcon icon) {
        if (block.shouldSideBeRendered(world, x + 1, y, z, 6))
            renderer.renderFaceXPos(block, x, y, z, icon);
        if (block.shouldSideBeRendered(world, x - 1, y, z, 6))
            renderer.renderFaceXNeg(block, x, y, z, icon);
        if (block.shouldSideBeRendered(world, x, y, z + 1, 6))
            renderer.renderFaceZPos(block, x, y, z, icon);
        if (block.shouldSideBeRendered(world, x, y, z - 1, 6))
            renderer.renderFaceZNeg(block, x, y, z, icon);
        if (block.shouldSideBeRendered(world, x, y + 1, z, 6))
            renderer.renderFaceYPos(block, x, y, z, icon);
        if (block.shouldSideBeRendered(world, x, y - 1, z, 6))
            renderer.renderFaceYNeg(block, x, y, z, icon);
    }

    private static void drawBlockFaces(RenderBlocks renderer, Block block, IIcon icon) {
        drawBlockFaces(renderer, block, icon, icon, icon, icon, icon, icon);
    }

    private static void drawBlockFaces(RenderBlocks renderer, Block block, IIcon i0, IIcon i1, IIcon i2, IIcon i3, IIcon i4,
            IIcon i5) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, 0.0F, -0.5F);

        if (block != null) {
            tessellator.startDrawingQuads();
            tessellator.setNormal(0F, -1F, 0F);
            renderer.renderFaceYNeg(block, 0D, -0.5D, 0D, i0);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0F, 1F, 0F);
            renderer.renderFaceYPos(block, 0D, -0.5D, 0D, i1);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0F, 0F, -1F);
            renderer.renderFaceZNeg(block, 0D, -0.5D, 0D, i2);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0F, 0F, 1F);
            renderer.renderFaceZPos(block, 0D, -0.5D, 0D, i3);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1F, 0F, 0F);
            renderer.renderFaceXNeg(block, 0D, -0.5D, 0D, i4);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1F, 0F, 0F);
            renderer.renderFaceXPos(block, 0D, -0.5D, 0D, i5);
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.0F, 0.5F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

}
