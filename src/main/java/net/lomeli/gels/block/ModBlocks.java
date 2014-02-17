package net.lomeli.gels.block;

import java.awt.Color;

import net.lomeli.gels.block.gel.BlockPropolsionGel;
import net.lomeli.gels.block.gel.BlockReplusionGel;
import net.lomeli.gels.core.GelRegistry;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;

public class ModBlocks {
    public static Block propulsionGel, repulsionGel, adhesiveGel;

    public static void loadBlocks() {
        propulsionGel = new BlockPropolsionGel().setBlockName("proGel");
        repulsionGel = new BlockReplusionGel().setBlockName("repGel");

        GameRegistry.registerBlock(propulsionGel, propulsionGel.getUnlocalizedName());
        GameRegistry.registerBlock(repulsionGel, repulsionGel.getUnlocalizedName());

        GelRegistry.getInstance().addBlock(propulsionGel, new Color(255, 140, 0));
        GelRegistry.getInstance().addBlock(repulsionGel, new Color(40, 0, 255));
    }
}
