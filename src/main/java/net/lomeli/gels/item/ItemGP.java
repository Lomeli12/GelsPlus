package net.lomeli.gels.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.core.Strings;

public class ItemGP extends Item {
    @SideOnly(Side.CLIENT)
    protected String itemTexture;

    public ItemGP(int id) {
        super(id);
        this.setCreativeTab(GelsPlus.modTab);
    }

    public ItemGP(int id, String texture) {
        super(id);
        this.itemTexture = texture;
        this.setCreativeTab(GelsPlus.modTab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + this.itemTexture);
    }
}
