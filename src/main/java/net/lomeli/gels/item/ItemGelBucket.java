package net.lomeli.gels.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.gels.block.gel.BlockGel;
import net.lomeli.gels.core.GelRegistry;
import net.lomeli.gels.core.Strings;

public class ItemGelBucket extends ItemGP {
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public ItemGelBucket(int id) {
        super(id, "bucket_");
        setUnlocalizedName("GelBucket");
        setHasSubtypes(true);
        setContainerItem(Item.bucketEmpty);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + itemTexture + 0);
        iconArray = new Icon[4];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + itemTexture + i);
        }
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        ItemStack returnItem = itemStack;
        if (!player.capabilities.isCreativeMode)
            returnItem = new ItemStack(Item.bucketEmpty);

        int newX = x, newY = y, newZ = z;
        switch (side) {
        case 0:
            newY--;
            break;
        case 1:
            newY++;
            break;
        case 2:
            newZ--;
            break;
        case 3:
            newZ++;
            break;
        case 4:
            newX--;
            break;
        case 5:
            newX++;
            break;
        default:
            newY++;
            break;
        }

        if (!world.isRemote) {
            if (side > 3) {
                if (side == 5)
                    side = 2;
                else
                    side = 3;
            } else if (side > 1)
                side += 2;
            int newSide = ForgeDirection.OPPOSITES[side];
            int newBlock = GelRegistry.getInstance().getBlock(itemStack.getItemDamage());

            if (newBlock > 0 && world.isAirBlock(newX, newY, newZ) && BlockGel.canGelStay(world, newX, newY, newZ, newSide)) {
                if (newSide == 2)
                    newSide = 3;
                else if (newSide == 3)
                    newSide = 2;
                world.setBlock(newX, newY, newZ, newBlock, newSide, 2);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, returnItem);
            }
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int meta) {
        return meta < iconArray.length ? iconArray[meta] : iconArray[0];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < 4; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
