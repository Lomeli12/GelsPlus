package net.lomeli.gels.item;

import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item gelBucket, blob, gelBlob, gelShield, longfall;

    public static void loadItems() {
        blob = new ItemGP("gelBlob").setUnlocalizedName("gelBlob");
        GameRegistry.registerItem(blob, "blob");

        gelShield = new ItemShield().setUnlocalizedName("gelShield");
        GameRegistry.registerItem(gelShield, "gelShield");

        gelBucket = new ItemGelBucket();
        GameRegistry.registerItem(gelBucket, "gelBucket");

        gelBlob = new ItemGelBlob();
        GameRegistry.registerItem(gelBlob, "gelBlob");

        longfall = new ItemLongFallBoots();
        GameRegistry.registerItem(longfall, "longfall");
    }
}
