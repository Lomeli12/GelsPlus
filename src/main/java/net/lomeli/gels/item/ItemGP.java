package net.lomeli.gels.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.core.Strings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGP extends Item {
    protected String itemTexture;

    public ItemGP() {
        super();
        this.setCreativeTab(GelsPlus.modTab);
    }

    public ItemGP(String texture) {
        super();
        this.itemTexture = texture;
        this.setCreativeTab(GelsPlus.modTab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + this.itemTexture);
    }
}
