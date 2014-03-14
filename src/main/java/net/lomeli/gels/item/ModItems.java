package net.lomeli.gels.item;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.core.ModIds;

public class ModItems {
    public static Item gelBucket, blob, gelBlob, debugBoots;

    public static void loadItems() {
        blob = new ItemGP(ModIds.blob, "gelBlob").setUnlocalizedName("gelBlob");
        GameRegistry.registerItem(blob, blob.getUnlocalizedName());

        gelBucket = new ItemGelBucket(ModIds.gelBucket);
        GameRegistry.registerItem(gelBucket, gelBucket.getUnlocalizedName());

        gelBlob = new ItemGelBlob(ModIds.gelBlob);
        GameRegistry.registerItem(gelBlob, gelBlob.getUnlocalizedName());

        if (GelsPlus.debugMode) {
            debugBoots = new ItemDebugBoots(ModIds.longFallBoots);
            GameRegistry.registerItem(debugBoots, debugBoots.getUnlocalizedName());
        }
    }
}
