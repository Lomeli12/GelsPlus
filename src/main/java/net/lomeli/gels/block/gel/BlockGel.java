package net.lomeli.gels.block.gel;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import net.lomeli.gels.block.BlockGP;
import net.lomeli.gels.item.ModItems;

public class BlockGel extends BlockGP implements IGel {

    public BlockGel(int id) {
        super(id, Material.piston);
        this.setBlockUnbreakable();
        this.setBlockBounds(0F, 0F, 0F, 1F, 0.01F, 1F);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par, float par1,
            float par2) {
        if(!world.isRemote) {
            if(player != null) {
                ItemStack stack = player.getCurrentEquippedItem();
                if(stack != null && stack.getUnlocalizedName().equals(Item.bucketEmpty.getUnlocalizedName())) {
                    Block bk = Block.blocksList[world.getBlockId(x, y, z)];
                    if(bk instanceof IGel) {
                        int j = ((IGel) bk).getGelID();
                        if(!player.capabilities.isCreativeMode) {
                            if(stack.stackSize == 1)
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(
                                        ModItems.gelBucket, 1, j));
                            else {
                                EntityItem item = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(
                                        ModItems.gelBucket, 1, j));
                                stack.stackSize--;
                                world.spawnEntityInWorld(item);
                            }
                        }

                        world.setBlockToAir(x, y, z);
                    }
                }
            }
        }
        return false;
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
    public boolean isBlockSolid(IBlockAccess p_149747_1_, int p_149747_2_, int p_149747_3_, int p_149747_4_, int p_149747_5_) {
        return false;
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);

        switch(meta) {
        case 0:
            return world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP);
        case 1:
            return world.isBlockSolidOnSide(x, y + 1, z, ForgeDirection.DOWN);
        case 2:
            return world.isBlockSolidOnSide(x + 1, y, z, ForgeDirection.WEST);
        case 3:
            return world.isBlockSolidOnSide(x - 1, y, z, ForgeDirection.EAST);
        case 4:
            return world.isBlockSolidOnSide(x, y, z - 1, ForgeDirection.SOUTH);
        case 5:
            return world.isBlockSolidOnSide(x, y, z + 1, ForgeDirection.NORTH);
        default:
            return world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP);
        }
    }

    public static boolean canGelStay(World world, int x, int y, int z, int meta) {
        switch(meta) {
        case 0:
            return world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP) && !(Block.blocksList[world.getBlockId(x, y - 1, z)] instanceof IGel);
        case 1:
            return world.isBlockSolidOnSide(x, y + 1, z, ForgeDirection.DOWN) && !(Block.blocksList[world.getBlockId(x, y + 1, z)] instanceof IGel);
        case 2:
            return world.isBlockSolidOnSide(x + 1, y, z, ForgeDirection.WEST) && !(Block.blocksList[world.getBlockId(x - 1, y, z)] instanceof IGel);
        case 3:
            return world.isBlockSolidOnSide(x - 1, y, z, ForgeDirection.EAST) && !(Block.blocksList[world.getBlockId(x + 1, y, z)] instanceof IGel);
        case 4:
            return world.isBlockSolidOnSide(x, y, z - 1, ForgeDirection.SOUTH) && !(Block.blocksList[world.getBlockId(x, y, z - 1)] instanceof IGel);
        case 5:
            return world.isBlockSolidOnSide(x, y, z + 1, ForgeDirection.NORTH) && !(Block.blocksList[world.getBlockId(x, y, z + 1)] instanceof IGel);
        default:
            return world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP) && !(Block.blocksList[world.getBlockId(x, y - 1, z)] instanceof IGel);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP) || world.isBlockSolidOnSide(x, y + 1, z, ForgeDirection.DOWN)
                || world.isBlockSolidOnSide(x - 1, y, z, ForgeDirection.EAST) || world.isBlockSolidOnSide(x + 1, y, z, ForgeDirection.WEST)
                || world.isBlockSolidOnSide(x, y, z + 1, ForgeDirection.NORTH) || world.isBlockSolidOnSide(x, y, z - 1, ForgeDirection.SOUTH);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        boolean doEffect = true;
        if(entity instanceof EntityPlayer)
            doEffect = !((EntityPlayer) entity).isSneaking();
        doGelEffect(world, x, y, z, entity, doEffect);
    }

    public void doGelEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
    }

    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int side, float par6, float par7, float par8, int par9) {
        int j1 = par9;

        if(side == 0)
            j1 = 1;

        if(side == 1)
            j1 = 0;

        if(side == 2)
            j1 = 5;

        if(side == 3)
            j1 = 4;

        if(side == 4)
            j1 = 3;

        if(side == 5)
            j1 = 2;

        return j1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    public void setBlockBoundsForItemRender() {
        func_111047_d(0);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess par1IWorld, int par2, int par3, int par4) {
        func_111047_d(par1IWorld.getBlockMetadata(par2, par3, par4));
    }

    protected void func_111047_d(int par1) {
        float lw = 0.0125F;
        float hi = 0.9375F;
        switch(par1) {
        case 1:
            setBlockBounds(0.0F, hi, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
        case 0:
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, lw, 1.0F);
            break;
        case 3:
            setBlockBounds(hi, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
        case 2:
            setBlockBounds(0.0F, 0.0F, 0.0F, lw, 1.0F, 1.0F);
            break;
        case 5:
            setBlockBounds(0.0F, 0.0F, hi, 1.0F, 1.0F, 1.0F);
            break;
        case 4:
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, lw);
        }
    }

    @Override
    public int getGelID() {
        return 0;
    }
}
