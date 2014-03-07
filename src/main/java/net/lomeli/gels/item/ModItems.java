package net.lomeli.gels.item;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;

import net.lomeli.gels.GelsPlus;

public class ModItems {
    public static Item gelBucket, gelBlob, debugBoots;

    public static void loadItems() {
        gelBucket = new ItemGelBucket();
        GameRegistry.registerItem(gelBucket, gelBucket.getUnlocalizedName());

        gelBlob = new ItemGelBlob();
        GameRegistry.registerItem(gelBlob, gelBlob.getUnlocalizedName());

        if (GelsPlus.debugMode) {
            debugBoots = new ItemDebugBoots();
            GameRegistry.registerItem(debugBoots, debugBoots.getUnlocalizedName());
        }
    }
}
