package net.lomeli.gels.item;

import java.util.List;

import net.lomeli.gels.core.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemGelBucket extends ItemGP {
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public ItemGelBucket() {
        super("bucket_");
        setUnlocalizedName("GelBucket");
        setHasSubtypes(true);
        setContainerItem(Items.bucket);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + itemTexture + 0);
        iconArray = new IIcon[4];
        for(int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + itemTexture + i);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta) {
        return meta < iconArray.length ? iconArray[meta] : iconArray[0];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for(int i = 0; i < 4; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
