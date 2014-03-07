package net.lomeli.gels.block;

import java.awt.Color;

import net.lomeli.gels.block.gel.BlockPropolsionGel;
import net.lomeli.gels.block.gel.BlockReplusionGel;
import net.lomeli.gels.core.GelRegistry;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ModBlocks {
    public static int gelRenderID;
    public static Block propulsionGel, repulsionGel, adhesiveGel;

    public static void loadBlocks() {
        propulsionGel = new BlockPropolsionGel().setBlockName("proGel");
        repulsionGel = new BlockReplusionGel().setBlockName("repGel");

        GameRegistry.registerBlock(propulsionGel, propulsionGel.getUnlocalizedName());
        GameRegistry.registerBlock(repulsionGel, repulsionGel.getUnlocalizedName());

        GelRegistry.getInstance().addBlock(propulsionGel, new Color(255, 140, 0), new ItemStack(Items.sugar));
        GelRegistry.getInstance().addBlock(repulsionGel, new Color(0, 70, 255), new ItemStack(Items.slime_ball));
    }
}
