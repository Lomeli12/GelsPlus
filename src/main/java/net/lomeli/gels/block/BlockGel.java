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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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

import net.lomeli.lomlib.entity.EntityUtil;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.client.IconGel;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.gel.GelRegistry;
import net.lomeli.gels.item.ModItems;

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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
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
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
        connectedIcon = new IconGel(iconRegister, "gel/gel", Strings.MODID.toLowerCase());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        TileGel tile = (TileGel)world.getTileEntity(x, y, z);
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
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par, float par1,
                                    float par2) {
        if (!world.isRemote) {
            TileGel tile = (TileGel) world.getTileEntity(x, y, z);
            if (tile != null) {
                ItemStack stack = player.getCurrentEquippedItem();
                if (stack != null && stack.getUnlocalizedName().equals(Items.bucket.getUnlocalizedName())) {
                    ItemStack newStack = new ItemStack(ModItems.gelBucket, 1, world.getBlockMetadata(x, y, z));
                    if (!player.capabilities.isCreativeMode && newStack != null) {
                        if (tile.canPickUp()) {
                            player.getCurrentEquippedItem().stackSize--;
                            EntityItem item = new EntityItem(world, player.posX, player.posY, player.posZ, newStack);
                            world.spawnEntityInWorld(item);
                        }
                    }
                    world.setBlockToAir(x, y, z);
                    return true;
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
        return world.isSideSolid(x, y - 1, z, ForgeDirection.UP) || world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN)
                || world.isSideSolid(x - 1, y, z, ForgeDirection.EAST) || world.isSideSolid(x + 1, y, z, ForgeDirection.WEST)
                || world.isSideSolid(x, y, z + 1, ForgeDirection.NORTH) || world.isSideSolid(x, y, z - 1, ForgeDirection.SOUTH);
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
    public IIcon getIcon (int par1, int par2) {
        return connectedIcon == null ? this.blockIcon : connectedIcon.icons[0];
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        if (meta < GelRegistry.INSTANCE().getRegistry().size()) {
            GelAbility gel = GelRegistry.INSTANCE().getGel(meta);
            return gel != null ? gel.gelColor().getRGB() : Color.WHITE.getRGB();
        }
        return Color.WHITE.getRGB();
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        if (meta < GelRegistry.INSTANCE().getRegistry().size()) {
            GelAbility gel = GelRegistry.INSTANCE().getGel(meta);
            return gel != null ? gel.gelColor().getRGB() : Color.WHITE.getRGB();
        }
        return Color.WHITE.getRGB();
    }

    public IIcon getConnectedBlockTexture(IBlockAccess par1IBlockAccess, int x, int y, int z, int side, IIcon[] icons) {
        boolean isOpenUp = false, isOpenDown = false, isOpenLeft = false, isOpenRight = false, downLeft = false, downRight = false, upRight = false, upLeft = false;

        switch (side) {
            case 0:
                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y, z), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y, z), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y, z - 1), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y, z + 1), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    isOpenRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y, z - 1), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    upLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y, z + 1), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    downLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y, z - 1), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    upRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y, z + 1), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    downRight = true;
                }
                break;
            case 1:
                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y, z), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y, z), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y, z - 1), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y, z + 1), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    isOpenRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y, z - 1), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    upLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y, z + 1), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    downLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y, z - 1), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    upRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y, z + 1), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    downRight = true;
                }
                break;
            case 2:
                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y - 1, z), par1IBlockAccess.getBlockMetadata(x, y - 1, z))) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y + 1, z), par1IBlockAccess.getBlockMetadata(x, y + 1, z))) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y, z - 1), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y, z + 1), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    isOpenRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y - 1, z - 1), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    downLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y - 1, z + 1), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    downRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y + 1, z - 1), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    upLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y + 1, z + 1), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    upRight = true;
                }
                break;
            case 3:
                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y - 1, z), par1IBlockAccess.getBlockMetadata(x, y - 1, z))) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y + 1, z), par1IBlockAccess.getBlockMetadata(x, y + 1, z))) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y, z - 1), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y, z + 1), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    isOpenRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y - 1, z - 1), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    downLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y - 1, z + 1), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    downRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y + 1, z - 1), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    upLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y + 1, z + 1), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    upRight = true;
                }
                break;
            case 4:
                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y - 1, z), par1IBlockAccess.getBlockMetadata(x, y - 1, z))) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y + 1, z), par1IBlockAccess.getBlockMetadata(x, y + 1, z))) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y, z), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y, z), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    isOpenRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y - 1, z), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    downLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y - 1, z), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    downRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y + 1, z), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    upLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y + 1, z), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    upRight = true;
                }
                break;
            case 5:
                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y - 1, z), par1IBlockAccess.getBlockMetadata(x, y - 1, z))) {
                    isOpenDown = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x, y + 1, z), par1IBlockAccess.getBlockMetadata(x, y + 1, z))) {
                    isOpenUp = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y, z), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    isOpenLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y, z), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    isOpenRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y - 1, z), par1IBlockAccess.getBlockMetadata(x - 1, y, z))) {
                    downLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y - 1, z), par1IBlockAccess.getBlockMetadata(x + 1, y, z))) {
                    downRight = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x - 1, y + 1, z), par1IBlockAccess.getBlockMetadata(x, y, z - 1))) {
                    upLeft = true;
                }

                if (shouldConnectToBlock(par1IBlockAccess, x, y, z, par1IBlockAccess.getBlock(x + 1, y + 1, z), par1IBlockAccess.getBlockMetadata(x, y, z + 1))) {
                    upRight = true;
                }
                break;
        }
        if (side > 1 && side < 6) {
            if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                if (upRight && upLeft && downRight && downLeft) {
                    return icons[15];
                } else if (upRight && upLeft && downLeft) {
                    return icons[26];
                } else if (upRight && upLeft && downRight) {
                    return icons[25];
                } else if (upRight && downRight && downLeft) {
                    return icons[27];
                } else if (upLeft && downRight && downLeft) {
                    return icons[28];
                } else if (upLeft && upRight) {
                    return icons[29];
                } else if (downLeft && downRight) {
                    return icons[30];
                } else if (downLeft && upLeft) {
                    return icons[32];
                } else if (downRight && upRight) {
                    return icons[31];
                } else if (upLeft && downRight) {
                    return icons[41];
                } else if (downLeft && upRight) {
                    return icons[42];
                } else if (upLeft) {
                    return icons[35];
                } else if (upRight) {
                    return icons[36];
                } else if (downLeft) {
                    return icons[33];
                } else if (downRight) {
                    return icons[34];
                }
                return icons[16];

                // Edges
            } else if (isOpenUp && isOpenDown && isOpenLeft) {
                if (upLeft && downLeft)
                    return icons[13];
                else if (upLeft)
                    return icons[37];
                else if (downLeft)
                    return icons[39];
                return icons[23];
            } else if (isOpenUp && isOpenDown && isOpenRight) {
                if (upRight && downRight)
                    return icons[12];
                else if (upRight)
                    return icons[38];
                else if (downRight)
                    return icons[40];
                return icons[24];
            } else if (isOpenUp && isOpenLeft && isOpenRight) {
                if(upLeft && upRight)
                    return icons[11];
                else if (upLeft)
                    return icons[46];
                else if (upRight)
                    return icons[45];
                return icons[22];
            } else if (isOpenDown && isOpenLeft && isOpenRight) {
                if (downLeft && downRight)
                    return icons[14];
                else if (downLeft)
                    return icons[44];
                else if (downRight)
                    return icons[43];
                return icons[21];

            // Horizontal and Vertical
            } else if (isOpenDown && isOpenUp) {
                return icons[10];
            } else if (isOpenLeft && isOpenRight) {
                return icons[7];

                // Corners
            } else if (isOpenDown && isOpenLeft) {
                return downLeft ? icons[9] : icons[20];
            } else if (isOpenDown && isOpenRight) {
                return downRight ? icons[8] : icons[19];
            } else if (isOpenUp && isOpenLeft) {
                return upLeft ? icons[6] : icons[18];
            } else if (isOpenUp && isOpenRight) {
                return upRight ? icons[5] : icons[17];

                // Directional
            } else if (isOpenDown) {
                return icons[1];
            } else if (isOpenUp) {
                return icons[4];
            } else if (isOpenLeft) {
                return icons[2];
            } else if (isOpenRight) {
                return icons[3];
            }
        } else {
            if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                if (upRight && upLeft && downRight && downLeft) {
                    return icons[15];
                } else if (upRight && upLeft && downLeft) {
                    return icons[26];
                } else if (upRight && upLeft && downRight) {
                    return icons[25];
                } else if (upRight && downRight && downLeft) {
                    return icons[27];
                } else if (upLeft && downRight && downLeft) {
                    return icons[28];
                } else if (upLeft && upRight) {
                    return icons[29];
                } else if (downLeft && downRight) {
                    return icons[30];
                } else if (downLeft && upLeft) {
                    return icons[32];
                } else if (downRight && upRight) {
                    return icons[31];
                } else if (upLeft) {
                    return icons[35];
                } else if (upRight) {
                    return icons[36];
                } else if (downLeft) {
                    return icons[33];
                } else if (downRight) {
                    return icons[34];
                }
                return icons[16];

            // Edges
            } else if (isOpenUp && isOpenDown && isOpenLeft) {
                // 13, 37, 39
                if (upLeft && upRight)
                    return icons[11];
                else if (upLeft)
                    return icons[46];
                else if (upRight)
                    return icons[45];
                return icons[22];
            } else if (isOpenUp && isOpenDown && isOpenRight) {
                // 12, 38, 40
                if (downLeft && downRight)
                    return icons[14];
                else if (downLeft)
                    return icons[44];
                else if (downRight)
                    return icons[43];
                return icons[21];
            } else if (isOpenUp && isOpenLeft && isOpenRight) {
                // 11, 46, 45
                if(upLeft && upRight)
                    return icons[11];
                else if (upLeft)
                    return icons[46];
                else if (upRight)
                    return icons[45];
                return icons[12];
            } else if (isOpenDown && isOpenLeft && isOpenRight) {
                // 14, 44, 43
                if (downLeft && downRight)
                    return icons[14];
                else if (downLeft)
                    return icons[44];
                else if (downRight)
                    return icons[43];
                return icons[13];

            // Horizontal and Vertical
            } else if (isOpenDown && isOpenUp) {
                return icons[7];
            } else if (isOpenLeft && isOpenRight) {
                return icons[10];

            // Corners
            } else if (isOpenDown && isOpenLeft) {
                return downLeft ? icons[9] : icons[6];
            } else if (isOpenDown && isOpenRight) {
                return downRight ? icons[8] : icons[9];
            } else if (isOpenUp && isOpenLeft) {
                return upLeft ? icons[6] : icons[5];
            } else if (isOpenUp && isOpenRight) {
                return upRight ? icons[5] : icons[8];

            // Directions
            } else if (isOpenDown) {
                return icons[2];
            } else if (isOpenUp) {
                return icons[3];
            } else if (isOpenLeft) {
                return icons[4];
            } else if (isOpenRight) {
                return icons[1];
            }
        }
        return shouldSideBeRendered(par1IBlockAccess, x, y, z, side) ? icons[0] : this.blockIcon;
    }


    public boolean shouldConnectToBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int meta) {

        return block == this && meta == blockAccess.getBlockMetadata(x, y, z);
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

        @Override
        public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
                                    float hitY, float hitZ, int metadata) {
            if (!world.setBlock(x, y, z, this.field_150939_a, metadata, 3)) {
                return false;
            }

            if (this.field_150939_a != null && world.getBlock(x, y, z) == this.field_150939_a) {
                this.field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
                this.field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
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
            }

            return true;
        }

        @Override
        public String getItemStackDisplayName(ItemStack stack) {
            return stack.getItemDamage() < GelRegistry.INSTANCE().getRegistry().size() ? StatCollector
                    .translateToLocal(GelRegistry.INSTANCE().getGel(stack.getItemDamage()).gelName())
                    + " "
                    + super.getItemStackDisplayName(stack) : super.getItemStackDisplayName(stack);
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

        public String getStepResourcePath() {
            return "mob.slime.big";
        }
    }
}
