package net.lomeli.gels.client;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import net.lomeli.gels.block.BlockGel;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;
import net.lomeli.gels.core.GelRegistry;

import net.lomeli.lomlib.client.render.RenderUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderGels implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int meta, int model, RenderBlocks renderer) {
        Color blockColor = GelRegistry.getInstance().getGel(meta).gelColor();

        float r = (blockColor.getRed() / 255f), g = (blockColor.getGreen() / 255f), b = (blockColor.getBlue() / 255f);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(r, g, b, 1);
        renderer.setRenderBounds(0F, 0F, 0F, 1F, 0.01F, 1F);
        RenderUtils.drawBlockFaces(renderer, block, block.getIcon(0, 0));
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int meta, RenderBlocks renderer) {
        int side = 0;
        TileGel tile = (TileGel) world.getTileEntity(x, y, z);
        if (tile != null) {
            side = tile.getSide();
            ((BlockGel) block).bounds(side);

            Color blockColor = tile.getAbility(world, x, y, z).gelColor();

            Tessellator tessellator = Tessellator.instance;
            tessellator.setColorOpaque_I(blockColor.getRGB());
            RenderUtils.renderBlock(world, x, y, z, block, renderer, ((BlockGel) block).getIcon(0, 0));

            renderer.clearOverrideBlockTexture();
            block.setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
            renderer.setRenderBoundsFromBlock(block);
        }
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
}
