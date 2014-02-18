package net.lomeli.gels.block;

import java.awt.Color;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.block.gel.BlockPropolsionGel;
import net.lomeli.gels.block.gel.BlockReplusionGel;
import net.lomeli.gels.core.GelRegistry;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModBlocks {
    public static Block propulsionGel, repulsionGel, adhesiveGel;

    public static void loadBlocks() {
        propulsionGel = new BlockPropolsionGel(GelsPlus.proGelID).setUnlocalizedName("proGel");
        repulsionGel = new BlockReplusionGel(GelsPlus.repGelID).setUnlocalizedName("repGel");

        GameRegistry.registerBlock(propulsionGel, propulsionGel.getUnlocalizedName());
        GameRegistry.registerBlock(repulsionGel, repulsionGel.getUnlocalizedName());

        GelRegistry.getInstance().addBlock(propulsionGel.blockID, new Color(255, 140, 0), new ItemStack(Item.sugar));
        GelRegistry.getInstance().addBlock(repulsionGel.blockID, new Color(0, 70, 255), new ItemStack(Item.slimeBall));
    }
}
