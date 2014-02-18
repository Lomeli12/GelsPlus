package net.lomeli.gels.item;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.gels.GelsPlus;

import net.minecraft.item.Item;

public class ModItems {
    public static Item brush, gelOrb, throwableGel, gelBucket, gelBlob, debugBoots;

    public static void loadItems() {
        gelBucket = new ItemGelBucket(GelsPlus.gelBucketID);
        GameRegistry.registerItem(gelBucket, gelBucket.getUnlocalizedName());

        debugBoots = new ItemDebugBoots(GelsPlus.longFallID);
        GameRegistry.registerItem(debugBoots, debugBoots.getUnlocalizedName());

        gelBlob = new ItemGelBlob(GelsPlus.gelBlobID);
        GameRegistry.registerItem(gelBlob, gelBlob.getUnlocalizedName());
    }
}
