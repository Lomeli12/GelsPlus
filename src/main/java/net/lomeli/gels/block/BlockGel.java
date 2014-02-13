package net.lomeli.gels.block;

import java.util.List;
import java.util.Random;

import net.lomeli.gels.core.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGel extends BlockGP implements IGel {
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public BlockGel() {
        super(Material.circuits, "gel_");
        this.setBlockUnbreakable();
        this.setBlockBounds(0F, 0F, 0F, 1F, 0.035625F, 1F);
        this.setBlockName("gel");
        this.setTickRandomly(true);
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
    public int getRenderType() {
        return 23;
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        Material mat = world.getBlock(x, y - 1, z).getMaterial();
        return mat != Material.air || mat != Material.cactus || mat != Material.water;
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        iconArray = new IIcon[3];
        for(int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + this.blockTexture);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
        return null;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        int meta = world.getBlockMetadata(x, y, z);
        switch(meta) {
        case 0:
            if((entity.motionX > 0.1D || entity.motionX < -0.1D) && (entity.motionZ > 0.1D || entity.motionZ < -0.1D)) {
                double mov = 0.06D;
                if(entity.motionX > 0.1D) {
                    entity.motionX += mov;
                }else if(entity.motionX < -0.1D) {
                    entity.motionX -= mov;
                }

                if(entity.motionZ > 0.1D) {
                    entity.motionZ += mov;
                }else if(entity.motionZ < -0.1D) {
                    entity.motionZ -= mov;
                }
            }else {
                double mov = 0.11D;
                if(entity.motionX > 0.1D) {
                    entity.motionX += mov;
                }else if(entity.motionX < -0.1D) {
                    entity.motionX -= mov;
                }

                if(entity.motionZ > 0.1D) {
                    entity.motionZ += mov;
                }else if(entity.motionZ < -0.1D) {
                    entity.motionZ -= mov;
                }
            }
            break;
        case 1:
            entity.fallDistance = 0;
            entity.motionY = 1;
            break;
        case 2:
            entity.fallDistance = 0;
            entity.motionY = 0.001D;
            break;
        default:
            break;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for(int i = 0; i < 3; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }

    @Override
    public IIcon getIcon(int par1, int par2) {
        return this.iconArray[par2 % this.iconArray.length];
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return this.damageDropped(world.getBlockMetadata(x, y, z));
    }

    public static class ItemBlockGel extends ItemBlock {

        public ItemBlockGel(Block par1) {
            super(ModBlocks.otherGels);
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }

        @Override
        public IIcon getIconFromDamage(int par1) {
            return this.field_150939_a.getIcon(0, par1);
        }

        @Override
        public int getMetadata(int meta) {
            return meta;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack) {
            return this.field_150939_a.getUnlocalizedName() + "." + stack.getItemDamage();
        }
    }
}
