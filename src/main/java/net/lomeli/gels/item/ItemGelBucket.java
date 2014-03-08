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

import net.lomeli.gels.block.BlockGel;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;
import net.lomeli.gels.core.GelRegistry;
import net.lomeli.gels.core.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
        if (renderPass == 0) {
            return iconArray[0];
        }else {
            return iconArray[1];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
        return (itemStack.getItemDamage() < GelRegistry.getInstance().getRegistry().size() && renderPass == 1) ? GelRegistry
                .getInstance().getGel(itemStack.getItemDamage()).gelColor().getRGB() : Color.WHITE.getRGB();
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
            float hitY, float hitZ) {
        ItemStack returnItem = itemStack;
        if (!player.capabilities.isCreativeMode)
            returnItem = new ItemStack(Items.bucket);

        int newX = x, newY = y, newZ = z;
        switch(side) {
        case 0 :
            newY--;
            break;
        case 1 :
            newY++;
            break;
        case 2 :
            newZ--;
            break;
        case 3 :
            newZ++;
            break;
        case 4 :
            newX--;
            break;
        case 5 :
            newX++;
            break;
        default:
            newY++;
            break;
        }

        if (!world.isRemote) {
            int newSide = 0;
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

            if (world.isAirBlock(newX, newY, newZ) && BlockGel.canGelStay(world, newX, newY, newZ, newSide)) {
                world.setBlock(newX, newY, newZ, ModBlocks.gel, itemStack.getItemDamage(), 2);
                TileGel tile = (TileGel) world.getTileEntity(newX, newY, newZ);
                if (tile != null) {
                    tile.setSide(newSide);
                }
                world.markBlockForUpdate(newX, newY, newZ);
                world.func_147479_m(newX, newY, newZ);
                world.getBlock(newX, newY, newZ).onNeighborBlockChange(world, newX, newY, newZ, world.getBlock(newX, newY, newZ));
                player.inventory.setInventorySlotContents(player.inventory.currentItem, returnItem);
            }
        }

        return false;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < GelRegistry.getInstance().getRegistry().size(); i++) {
            if (GelRegistry.getInstance().getGel(i) != null)
                list.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalizedName = stack.getUnlocalizedName();
        String gelName = stack.getItemDamage() < GelRegistry.getInstance().getRegistry().size() ? GelRegistry.getInstance()
                .getGel(stack.getItemDamage()).gelName() : "";
        return StatCollector.translateToLocal(gelName) + " " + StatCollector.translateToLocal(unlocalizedName);
    }
}
