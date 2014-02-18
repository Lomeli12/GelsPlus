package net.lomeli.gels.item;

import java.awt.Color;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.gels.core.GelRegistry;

public class ItemGelBlob extends ItemGP {

    public ItemGelBlob(int id) {
        super(id, "gelBlob");
        this.setUnlocalizedName("gelBlob");
        this.setHasSubtypes(true);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(int id, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < GelRegistry.getInstance().gelColor.size(); i++) {
            list.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
        return itemStack.getItemDamage() < GelRegistry.getInstance().gelColor.size() ? GelRegistry.getInstance()
                .getColor(itemStack.getItemDamage()).getRGB() : new Color(255, 255, 255).getRGB();
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        ItemStack blockstack = null;
        if (stack.getItemDamage() < GelRegistry.getInstance().gelRegistry.size())
            blockstack = new ItemStack(GelRegistry.getInstance().getBlock(stack.getItemDamage()), 1, 1);

        String unlocalizedName = stack.getUnlocalizedName();
        String gelName = blockstack != null ? blockstack.getDisplayName() + " " : "";
        return gelName + StatCollector.translateToLocal(unlocalizedName);
    }
}
