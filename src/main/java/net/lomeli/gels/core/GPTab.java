package net.lomeli.gels.core;

import net.lomeli.gels.item.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GPTab extends CreativeTabs {

    public GPTab() {
        super(CreativeTabs.getNextID(), Strings.MODID);
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.gelBucket;
    }

}
