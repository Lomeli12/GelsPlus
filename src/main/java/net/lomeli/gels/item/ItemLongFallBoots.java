package net.lomeli.gels.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemArmor;

import net.minecraftforge.common.util.EnumHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.core.Strings;

public class ItemLongFallBoots extends ItemArmor {

    public ItemLongFallBoots() {
        super(EnumHelper.addArmorMaterial("longfall", -1, new int[]{0, 0, 0, 0}, 0), 0, 3);
        this.setCreativeTab(GelsPlus.modTab);
        this.setUnlocalizedName("longfall");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":longfall");
    }
}
