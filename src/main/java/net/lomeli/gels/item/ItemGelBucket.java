package net.lomeli.gels.item;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.block.BlockGel;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;
import net.lomeli.gels.core.Strings;

public class ItemGelBucket extends ItemGP {
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public ItemGelBucket() {
        super("bucket_");
        setUnlocalizedName("GelBucket");
        setHasSubtypes(true);
        setContainerItem(Items.bucket);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + itemTexture + 0);
        iconArray = new IIcon[2];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + itemTexture + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public IIcon getIcon(ItemStack itemStack, int renderPass) {
        if (renderPass == 0)
            return iconArray[1];
        else
            return iconArray[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
        try {
            return (itemStack.getItemDamage() < GelsPlus.proxy.getRegistry().getRegistry().size() && renderPass == 0) ? (GelsPlus.proxy.getRegistry().getGel(itemStack.getItemDamage()).newInstance().gelColor() != null ? GelsPlus.proxy
                    .getRegistry().getGel(itemStack.getItemDamage()).newInstance().gelColor().getRGB()
                    : new Color(255, 255, 255).getRGB())
                    : Color.WHITE.getRGB();
        } catch (Exception e) {
            e.printStackTrace();
            return Color.WHITE.getRGB();
        }
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        int[] sides = { 1, 0, 5, 4, 3, 2 };
        switch (side) {
            case 0:
                y--;
                side = 1;
                break;
            case 1:
                y++;
                side = 0;
                break;
            case 2:
                z--;
                side = 5;
                break;
            case 3:
                z++;
                side = 4;
                break;
            case 4:
                x--;
                side = 3;
                break;
            case 5:
                x++;
                side = 2;
                break;
        }
        if (world.isAirBlock(x, y, z) && BlockGel.canGelStay(world, x, y, z, side))
            return BlockGel.ItemGel.placeGel(itemStack, ModBlocks.gel, player, world, x, y, z, sides[side], itemStack.getItemDamage(), true) && placed(player);
        else
            return false;
    }

    public boolean placed(EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
            return true;
        }
        return false;
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        if (!world.setBlock(x, y, z, ModBlocks.gel, metadata, 3))
            return false;

        if (world.getBlock(x, y, z) == ModBlocks.gel) {
            ModBlocks.gel.onBlockPlacedBy(world, x, y, z, player, stack);
            ModBlocks.gel.onPostBlockPlaced(world, x, y, z, metadata);
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
        }
        return true;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < GelsPlus.proxy.getRegistry().getRegistry().size(); i++) {
            if (GelsPlus.proxy.getRegistry().getGel(i) != null)
                list.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalizedName = stack.getUnlocalizedName();
        String gelName = null;
        try {
            gelName = stack.getItemDamage() < GelsPlus.proxy.getRegistry().getRegistry().size() ? GelsPlus.proxy.getRegistry().getGel(stack.getItemDamage()).newInstance().gelName() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StatCollector.translateToLocal(gelName) + " " + StatCollector.translateToLocal(unlocalizedName);
    }
}
