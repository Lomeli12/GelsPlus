package net.lomeli.gels.block;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.gels.block.BlockGel.ItemBlockGel;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ModBlocks {
    public static ItemStack[] gels;
    public static Block otherGels;

    public static void loadBlocks() {
        gels = new ItemStack[4];

        otherGels = new BlockGel();

        GameRegistry.registerBlock(otherGels, ItemBlockGel.class, otherGels.getUnlocalizedName());

        for(int i = 0; i < gels.length; i++) {
            gels[i] = new ItemStack(otherGels, 1, i);
        }
    }
}
