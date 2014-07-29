package net.lomeli.gels.core.multipart;

import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.TMultiPart;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.lomlib.libs.Strings;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;

public class RegisterPart implements IPartConverter, IPartFactory {

    @Override
    public TMultiPart createPart(String arg0, boolean arg1) {
        return arg0.equals(Strings.MOD_ID.toLowerCase() + "|gel") ? new PartGel() : null;
    }

    @Override
    public Iterable<Block> blockTypes() {
        Iterable<Block> bk = null;
        return bk;
    }

    @Override
    public TMultiPart convert(World arg0, BlockCoord arg1) {
        return isGel(arg0, arg1.x, arg1.y, arg1.z) ? new PartGel(arg0.getBlockMetadata(arg1.x, arg1.y, arg1.z)) : null;
    }

    public boolean isGel(World world, int x, int y, int z) {
        Block bk = world.getBlock(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tile = world.getTileEntity(x, y, z);
        if (bk != null && tile != null) {
            if (bk == ModBlocks.gel && (tile instanceof TileGel))
                return meta < GelsPlus.proxy.getRegistry().getRegistry().size();
        }
        return false;
    }

}
