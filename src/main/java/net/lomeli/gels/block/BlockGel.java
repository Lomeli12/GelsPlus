package net.lomeli.gels.block;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.client.CTManager;
import net.lomeli.gels.client.IconGel;
import net.lomeli.gels.core.Strings;

public class BlockGel extends BlockGP implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private IconGel connectedIcon;

    public BlockGel() {
        super(Material.circuits);
        this.blockTexture = "gel_default";
        this.setStepSound(new GelSound(1f, 1f));
        this.setBlockUnbreakable();
        this.setBlockBounds(0F, 0F, 0F, 1F, 0.01F, 1F);
    }

    public static boolean canGelStay(World world, int x, int y, int z, int side) {
        switch (side) {
            case 0:
                return world.isSideSolid(x, y - 1, z, ForgeDirection.UP) && !(world.getBlock(x, y - 1, z) instanceof IGel);
            case 1:
                return world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN) && !(world.getBlock(x, y + 1, z) instanceof IGel);
            case 2:
                return world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) && !(world.getBlock(x - 1, y, z) instanceof IGel);
            case 3:
                return world.isSideSolid(x + 1, y, z, ForgeDirection.WEST) && !(world.getBlock(x + 1, y, z) instanceof IGel);
            case 4:
                return world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH) && !(world.getBlock(x, y, z - 1) instanceof IGel);
            case 5:
                return world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) && !(world.getBlock(x, y, z + 1) instanceof IGel);
            default:
                return world.isSideSolid(x, y - 1, z, ForgeDirection.UP) && !(world.getBlock(x, y - 1, z) instanceof IGel);
        }
    }

    public static boolean canPlaceBlock(World world, int x, int y, int z) {
        return world.isSideSolid(x, y - 1, z, ForgeDirection.UP) || world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN) || world.isSideSolid(x - 1, y, z, ForgeDirection.EAST)
                || world.isSideSolid(x + 1, y, z, ForgeDirection.WEST) || world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) || world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < GelsPlus.proxy.getRegistry().getRegistry().size(); i++) {
            if (GelsPlus.proxy.getRegistry().getGel(i) != null)
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
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
        connectedIcon = new IconGel(iconRegister, "gel/gel", Strings.MODID.toLowerCase());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof TileGel) {
            TileGel tile = (TileGel) te;
            if (tile != null) {
                if (tile.getSide() < 2)
                    return side == ForgeDirection.OPPOSITES[tile.getSide()];
                else {
                    switch (tile.getSide()) {
                        case 2:
                            return side == 4;
                        case 3:
                            return side == 5;
                        case 4:
                            return side == 2;
                        case 5:
                            return side == 3;
                        default:
                            return false;
                    }
                }
            }
        }
        return true;
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
        TileGel tile = (TileGel) world.getTileEntity(x, y, z);
        if (tile != null)
            return canGelStay(world, x, y, z, tile.getSide());
        return true;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        return canPlaceBlockAt(world, x, y, z);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return canPlaceBlock(world, x, y, z);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        boolean doEffect = true;
        if (entity instanceof EntityPlayer)
            doEffect = !entity.isSneaking();
        TileGel tile = (TileGel) world.getTileEntity(x, y, z);
        if (tile != null)
            tile.doGelEffect(world, x, y, z, entity, doEffect);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!canBlockStay(world, x, y, z))
            world.setBlockToAir(x, y, z);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        TileGel tile = (TileGel) par1IBlockAccess.getTileEntity(par2, par3, par4);
        int side = 0;
        if (tile != null)
            side = tile.getSide();
        return connectedIcon == null ? this.blockIcon : getConnectedBlockTexture(par1IBlockAccess, par2, par3, par4, side, connectedIcon.icons);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2) {
        return connectedIcon == null ? this.blockIcon : connectedIcon.icons[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        if (meta < GelsPlus.proxy.getRegistry().getRegistry().size()) {
            GelAbility gel = null;
            try {
                gel = GelsPlus.proxy.getRegistry().getGel(meta).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return gel != null ? gel.gelColor().getRGB() : Color.WHITE.getRGB();
        }
        return Color.WHITE.getRGB();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        if (meta < GelsPlus.proxy.getRegistry().getRegistry().size()) {
            GelAbility gel = null;
            try {
                gel = GelsPlus.proxy.getRegistry().getGel(meta).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return gel != null ? gel.gelColor().getRGB() : Color.WHITE.getRGB();
        }
        return Color.WHITE.getRGB();
    }

    public IIcon getConnectedBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side, IIcon[] icons) {
        return CTManager.getConnectedBlockTexture(blockAccess, this, x, y, z, side, icons, this.blockIcon);
    }

    @Override
    public void setBlockBoundsForItemRender() {
        bounds(0);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        TileGel tile = (TileGel) world.getTileEntity(x, y, z);
        if (tile != null)
            bounds(tile.getSide());
    }

    public void bounds(int par1) {
        float lw = 0.0625f;
        float hi = 0.9375F;
        switch (par1) {
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
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileGel();
    }

    public static class ItemGel extends ItemBlock {

        public ItemGel(Block par1) {
            super(par1);
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }

        public static boolean placeGel(ItemStack stack, Block blk, EntityPlayer player, World world, int x, int y, int z, int side, int metadata, boolean playSound) {
            if (!world.setBlock(x, y, z, blk, metadata, 3))
                return false;

            if (blk != null && world.getBlock(x, y, z) == blk) {
                blk.onBlockPlacedBy(world, x, y, z, player, stack);
                blk.onPostBlockPlaced(world, x, y, z, metadata);
                TileGel gel = (TileGel) world.getTileEntity(x, y, z);
                if (gel != null) {
                    int newSide;
                    switch (side) {
                        case 0:
                            newSide = 1;
                            break;
                        case 1:
                            newSide = 0;
                            break;
                        case 2:
                            newSide = 5;
                            break;
                        case 3:
                            newSide = 4;
                            break;
                        case 4:
                            newSide = 3;
                            break;
                        case 5:
                            newSide = 2;
                            break;
                        default:
                            newSide = 1;
                            break;
                    }
                    gel.setSide(newSide);
                }
                world.markBlockForUpdate(x, y, z);
                world.func_147479_m(x, y, z);
                if (playSound)
                    world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), blk.stepSound.func_150496_b(), (blk.stepSound.getVolume() + 1.0F) / 2.0F,
                            blk.stepSound.getPitch() * 0.8F);
            }
            return true;
        }

        @Override
        public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
            return placeGel(stack, this.field_150939_a, player, world, x, y, z, side, metadata, false);
        }

        @Override
        public String getItemStackDisplayName(ItemStack stack) {
            if (stack.getItemDamage() < GelsPlus.proxy.getRegistry().getRegistry().size()) {
                GelAbility gel = null;
                try {
                    gel = GelsPlus.proxy.getRegistry().getGel(stack.getItemDamage()).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return gel != null ? StatCollector.translateToLocal(gel.gelName()) + " " + super.getItemStackDisplayName(stack) : super.getItemStackDisplayName(stack);
            }
            return super.getItemStackDisplayName(stack);
        }

        @Override
        public IIcon getIconFromDamage(int par1) {
            return ModBlocks.gel.getIcon(0, par1);
        }

        @Override
        public int getMetadata(int meta) {
            return meta;
        }
    }

    public static class GelSound extends SoundType {

        public GelSound(float vol, float freq) {
            super("slime", vol, freq);
        }

        @Override
        public String getBreakSound() {
            return "mob.slime.attack";
        }

        @Override
        public String getStepResourcePath() {
            return "mob.slime.big";
        }
    }
}
