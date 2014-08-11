package net.lomeli.gels.block;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static int gelRenderID, dispenserRenderID;
    public static Block gel, gelDispenser;

    public static void loadBlocks() {
        gel = new BlockGel().setBlockName("gel");
        GameRegistry.registerBlock(gel, BlockGel.ItemGel.class, "gel");

        gelDispenser = new BlockGelDispenser().setBlockName("gelDispenser");
        GameRegistry.registerBlock(gelDispenser, "gelDispenser");

    }
}
