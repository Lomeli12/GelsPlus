package net.lomeli.gels.client;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import net.lomeli.lomlib.client.render.RenderBlockCTM;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;

public class RenderGelBlock extends RenderBlockCTM {
    public RenderGelBlock(int id) {
        super(id);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        int color = getColor(metadata);
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        this.renderBlockInInventory(block, metadata, modelId, renderer, r, g, b);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int meta = world.getBlockMetadata(x, y, z);
        int color = getColor(meta);
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;
        RenderGelCT renderCT = new RenderGelCT(block, meta, renderer);
        return renderCT.renderCTBlock(world, x, y, z, r, g, b);
    }

    private int getColor(int meta) {
        int color = Color.WHITE.getRGB();
        if (meta < GelsPlus.proxy.getRegistry().getRegistry().size()) {
            GelAbility gel;
            try {
                gel = GelsPlus.proxy.getRegistry().getGel(meta).newInstance();
                color = gel.gelColor().getRGB();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return color;
    }
}
