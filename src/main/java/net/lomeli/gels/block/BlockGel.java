package net.lomeli.gels.block;

import java.util.List;

import net.lomeli.gels.core.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
        this.setBlockBounds(0F, 0F, 0F, 1F, 0.01F, 1F);
        this.setBlockName("gel");
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
    public boolean canBlockStay(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y - 1, z);
        Material mat = block.getMaterial();
        return !(block instanceof IGel) && (mat != Material.cactus || mat != Material.water);
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y - 1, z);
        Material mat = block.getMaterial();
        return !(block instanceof IGel) && (mat != Material.air || mat != Material.cactus || mat != Material.water);
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        iconArray = new IIcon[3];
        for(int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
        return null;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        int meta = world.getBlockMetadata(x, y, z);

        boolean flag = true;

        if(entity instanceof EntityPlayer)
            flag = !((EntityPlayer) entity).isSneaking();

        switch(meta) {
        case 0:
            double moveX = 0,
            moveZ = 0;
            if((entity.motionX > 0.1D || entity.motionX < -0.1D) && (entity.motionZ > 0.1D || entity.motionZ < -0.1D)) {
                double mov = 0.045D;
                if(entity.motionX > 0.1D)
                    moveX = mov;
                else if(entity.motionX < -0.1D)
                    moveX = -mov;

                if(entity.motionZ > 0.1D)
                    entity.motionZ = mov;
                else if(entity.motionZ < -0.1D)
                    entity.motionZ = -mov;
            }else {
                double mov = 0.1D;
                if(entity.motionX > 0.1D)
                    moveX = mov;
                else if(entity.motionX < -0.1D)
                    moveX = -mov;

                if(entity.motionZ > 0.1D)
                    moveZ = mov;
                else if(entity.motionZ < -0.1D)
                    moveZ = -mov;
            }

            if(flag) {
                entity.motionX += moveX;
                entity.motionZ += moveZ;
            }
            break;
        case 1:
            entity.fallDistance = 0;
            if(flag)
                entity.motionY = 1;
            break;
        case 2:
            entity.fallDistance = 0;
            if (entity.motionY < -0.06D)
                entity.motionY += 0.16D;
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
            return ModBlocks.otherGels.getIcon(0, par1);
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
