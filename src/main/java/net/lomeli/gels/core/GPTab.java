package net.lomeli.gels.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import net.lomeli.gels.item.ModItems;

public class GPTab extends CreativeTabs {

    public GPTab() {
        super(CreativeTabs.getNextID(), Strings.MODID);
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.gelBucket;
    }

}
