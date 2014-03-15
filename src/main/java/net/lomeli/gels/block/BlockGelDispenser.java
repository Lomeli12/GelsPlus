package net.lomeli.gels.block;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.gels.core.ModIds;
import net.lomeli.gels.item.ModItems;

public class BlockGelDispenser extends BlockGP implements ITileEntityProvider {

    public BlockGelDispenser(int id) {
        super(id, Material.rock, "gel");
        this.setHardness(3.5F);
        this.setStepSound(soundStoneFootstep);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY,
            float hitZ) {
        if (!world.isRemote) {
            if (player != null) {
                TileDispenser tile = (TileDispenser) world.getBlockTileEntity(x, y, z);
                if (tile != null) {
                    ItemStack stack = player.getCurrentEquippedItem();
                    if (stack != null && stack.getUnlocalizedName().equals(ModItems.gelBucket.getUnlocalizedName())) {
                        tile.setGelType(stack.getItemDamage());
                        world.markBlockForUpdate(x, y, z);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int getRenderType() {
        return ModBlocks.dispenserRenderID;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack) {
        ((TileDispenser) world.getBlockTileEntity(x, y, z)).setOrientation(world.getBlockMetadata(x, y, z));
        world.setBlockMetadataWithNotify(x, y, z, 0, 3);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int sideHit, float hitX, float hitY, float hitZ, int metaData) {
        return sideHit;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World paramWorld) {
        return new TileDispenser();
    }

    @Override
    public int idDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return ModIds.gelDispenser;
    }

}
