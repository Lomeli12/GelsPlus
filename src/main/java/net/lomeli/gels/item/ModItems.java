package net.lomeli.gels.item;

import net.minecraft.item.Item;

import net.lomeli.gels.GelsPlus;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item gelBucket, blob, gelBlob, debugBoots;

    public static void loadItems() {
        blob = new ItemGP("gelBlob").setUnlocalizedName("gelBlob");
        GameRegistry.registerItem(blob, blob.getUnlocalizedName());

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
