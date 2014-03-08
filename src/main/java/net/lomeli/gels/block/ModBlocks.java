package net.lomeli.gels.block;

import net.minecraft.block.Block;

import net.lomeli.gels.gel.GelPropulsion;
import net.lomeli.gels.gel.GelRegistry;
import net.lomeli.gels.gel.GelRepulsion;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static int gelRenderID;
    public static Block gel;

    public static void loadBlocks() {
        gel = new BlockGel().setBlockName("gel");
        GameRegistry.registerBlock(gel, BlockGel.ItemGel.class, gel.getUnlocalizedName());

        GelRegistry.getInstance().addGel(new GelPropulsion());
        GelRegistry.getInstance().addGel(new GelRepulsion());
    }
}
