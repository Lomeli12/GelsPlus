package net.lomeli.gels.item;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.item.Item;

public class ModItems {
    public static Item brush, gelOrb, throwableGel, gelBucket, debugBoots;

    public static void loadItems() {
        gelBucket = new ItemGelBucket();

        GameRegistry.registerItem(gelBucket, gelBucket.getUnlocalizedName());

        debugBoots = new ItemDebugBoots();
        GameRegistry.registerItem(debugBoots, debugBoots.getUnlocalizedName());
    }
}
