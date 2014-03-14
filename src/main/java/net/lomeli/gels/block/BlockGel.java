package net.lomeli.gels.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import net.lomeli.lomlib.client.render.IconConnected;
import net.lomeli.lomlib.client.render.IconConnectedReverse;

import net.lomeli.gels.core.Strings;
import net.lomeli.gels.gel.GelRegistry;
import net.lomeli.gels.item.ModItems;

public class BlockGel extends BlockGP implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private IconConnected connectedIcon;

    public BlockGel(int id) {
        super(id, Material.circuits);
        this.blockTexture = "gel_default";
        this.setStepSound(new GelSound(1f, 1f));
        this.setBlockUnbreakable();
        this.setBlockBounds(0F, 0F, 0F, 1F, 0.01F, 1F);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < GelRegistry.INSTANCE().getRegistry().size(); i++) {
            if (GelRegistry.INSTANCE().getGel(i) != null)
                par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return this.damageDropped(world.getBlockMetadata(x, y, z));
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        super.registerIcons(iconRegister);
        connectedIcon = new IconConnected(iconRegister, "gel", Strings.MODID.toLowerCase());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par, float par1,
            float par2) {
        if (!world.isRemote) {
            ItemStack stack = player.getCurrentEquippedItem();
            if (stack != null && stack.getUnlocalizedName().equals(Item.bucketEmpty.getUnlocalizedName())) {
                ItemStack newStack = new ItemStack(ModItems.gelBucket, 1, world.getBlockMetadata(x, y, z));
                if (!player.capabilities.isCreativeMode && newStack != null) {
                    player.getCurrentEquippedItem().stackSize--;
                    player.inventory.addItemStackToInventory(newStack);
                }
                world.setBlockToAir(x, y, z);
                return true;
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
        TileGel tile = (TileGel) world.getBlockTileEntity(x, y, z);
        if (tile != null) {
            return canGelStay(world, x, y, z, tile.getSide());
        }
        return true;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        return canPlaceBlockAt(world, x, y, z);
    }

    public static boolean canGelStay(World world, int x, int y, int z, int side) {
        switch(side) {
        case 0 :
            return world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP)
                    && !(Block.blocksList[world.getBlockId(x, y - 1, z)] instanceof IGel);
        case 1 :
            return world.isBlockSolidOnSide(x, y + 1, z, ForgeDirection.DOWN)
                    && !(Block.blocksList[world.getBlockId(x, y + 1, z)] instanceof IGel);
        case 2 :
            return world.isBlockSolidOnSide(x - 1, y, z, ForgeDirection.EAST)
                    && !(Block.blocksList[world.getBlockId(x - 1, y, z)] instanceof IGel);
        case 3 :
            return world.isBlockSolidOnSide(x + 1, y, z, ForgeDirection.WEST)
                    && !(Block.blocksList[world.getBlockId(x + 1, y, z)] instanceof IGel);
        case 4 :
            return world.isBlockSolidOnSide(x, y, z - 1, ForgeDirection.SOUTH)
                    && !(Block.blocksList[world.getBlockId(x, y, z - 1)] instanceof IGel);
        case 5 :
            return world.isBlockSolidOnSide(x, y, z + 1, ForgeDirection.NORTH)
                    && !(Block.blocksList[world.getBlockId(x, y, z + 1)] instanceof IGel);
        default:
            return world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP)
                    && !(Block.blocksList[world.getBlockId(x, y - 1, z)] instanceof IGel);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP)
                || world.isBlockSolidOnSide(x, y + 1, z, ForgeDirection.DOWN)
                || world.isBlockSolidOnSide(x - 1, y, z, ForgeDirection.EAST)
                || world.isBlockSolidOnSide(x + 1, y, z, ForgeDirection.WEST)
                || world.isBlockSolidOnSide(x, y, z + 1, ForgeDirection.NORTH)
                || world.isBlockSolidOnSide(x, y, z - 1, ForgeDirection.SOUTH);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        boolean doEffect = true;
        if (entity instanceof EntityPlayer)
            doEffect = !((EntityPlayer) entity).isSneaking();
        TileGel tile = (TileGel) world.getBlockTileEntity(x, y, z);
        if (tile != null) {
            tile.doGelEffect(world, x, y, z, entity, doEffect);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int block) {
        if (!canBlockStay(world, x, y, z))
            world.setBlockToAir(x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        return connectedIcon != null ? new IconConnectedReverse(connectedIcon) : this.blockIcon;
    }

    @Override
    public void setBlockBoundsForItemRender() {
        bounds(0);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        TileGel tile = (TileGel) world.getBlockTileEntity(x, y, z);
        if (tile != null) {
            bounds(tile.getSide());
        }
    }

    public void bounds(int par1) {
        float lw = 0.0625f;
        float hi = 0.9375F;
        switch(par1) {
        case 1 :
            setBlockBounds(0.0F, hi, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
        case 0 :
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, lw, 1.0F);
            break;
        case 3 :
            setBlockBounds(hi, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            break;
        case 2 :
            setBlockBounds(0.0F, 0.0F, 0.0F, lw, 1.0F, 1.0F);
            break;
        case 5 :
            setBlockBounds(0.0F, 0.0F, hi, 1.0F, 1.0F, 1.0F);
            break;
        case 4 :
            setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, lw);
        }
    }

    @Override
    public int idDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return 0;
    }

    @Override
    public int getRenderType() {
        return ModBlocks.gelRenderID;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileGel();
    }

    public static class ItemGel extends ItemBlock {

        public int blockID;

        public ItemGel(int par1) {
            super(par1);
            this.blockID = par1;
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }

        @Override
        public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
                float hitY, float hitZ, int metadata) {
            if (!world.setBlock(x, y, z, this.blockID, metadata, 3)) {
                return false;
            }

            if (world.getBlockId(x, y, z) == this.blockID) {
                Block.blocksList[this.blockID].onBlockPlacedBy(world, x, y, z, player, stack);
                Block.blocksList[this.blockID].onPostBlockPlaced(world, x, y, z, metadata);
                TileGel gel = (TileGel) world.getBlockTileEntity(x, y, z);
                if (gel != null) {
                    int newSide;
                    switch(side) {
                    case 0 :
                        newSide = 1;
                        break;
                    case 1 :
                        newSide = 0;
                        break;
                    case 2 :
                        newSide = 5;
                        break;
                    case 3 :
                        newSide = 4;
                        break;
                    case 4 :
                        newSide = 3;
                        break;
                    case 5 :
                        newSide = 2;
                        break;
                    default:
                        newSide = 1;
                        break;
                    }
                    gel.setSide(newSide);
                }
                world.markBlockForUpdate(x, y, z);
                world.markBlockForRenderUpdate(x, y, z);
            }

            return true;
        }

        @Override
        public String getItemDisplayName(ItemStack stack) {
            return stack.getItemDamage() < GelRegistry.INSTANCE().getRegistry().size() ? StatCollector
                    .translateToLocal(GelRegistry.INSTANCE().getGel(stack.getItemDamage()).gelName())
                    + " "
                    + super.getItemStackDisplayName(stack) : super.getItemStackDisplayName(stack);
        }

        @Override
        public Icon getIconFromDamage(int par1) {
            return ModBlocks.gel.getIcon(0, par1);
        }

        @Override
        public int getMetadata(int meta) {
            return meta;
        }

    }

    public static class GelSound extends StepSound {

        public GelSound(float vol, float freq) {
            super("slime", vol, freq);
        }

        @Override
        public String getBreakSound() {
            return "mob.slime.attack";
        }

        public String getStepResourcePath() {
            return "mob.slime.big";
        }
    }

}
