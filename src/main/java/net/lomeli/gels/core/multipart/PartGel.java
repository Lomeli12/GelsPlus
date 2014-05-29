package net.lomeli.gels.core.multipart;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;

import net.lomeli.lomlib.libs.Strings;

import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.minecraft.McMetaPart;

public class PartGel extends McMetaPart {

    public PartGel() {
        super(0);
    }

    public PartGel(int meta) {
        super(meta);
    }

    @Override
    public Cuboid6 getBounds() {
        TileEntity tile = this.world().getTileEntity(x(), y(), z());
        if (tile != null && tile instanceof TileGel) {
            TileGel gel = (TileGel) tile;
            return new Cuboid6(bounds(gel.getSide()));
        }
        return new Cuboid6(bounds(0));
    }

    public AxisAlignedBB bounds(int par1) {
        float lw = 0.0625f;
        float hi = 0.9375F;
        switch(par1) {
        case 1 :
            return AxisAlignedBB.getBoundingBox(0.0F, hi, 0.0F, 1.0F, 1.0F, 1.0F);
        case 2 :
            return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, lw, 1.0F, 1.0F);
        case 3 :
            return AxisAlignedBB.getBoundingBox(hi, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        case 4 :
            return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, lw);
        case 5 :
            return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, hi, 1.0F, 1.0F, 1.0F);
        default:
            return AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, lw, 1.0F);
        }
    }

    @Override
    public Block getBlock() {
        return ModBlocks.gel;
    }

    @Override
    public String getType() {
        return Strings.MOD_ID.toLowerCase() + "|gel";
    }

}
