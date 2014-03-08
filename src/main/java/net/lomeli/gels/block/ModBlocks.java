package net.lomeli.gels.block;

import net.minecraft.block.Block;

import net.lomeli.gels.core.GelRegistry;
import net.lomeli.gels.gel.GelPropolsion;
import net.lomeli.gels.gel.GelReplusion;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static int gelRenderID;
    public static Block gel;

    public static void loadBlocks() {
        gel = new BlockGel().setBlockName("gel");
        GameRegistry.registerBlock(gel, BlockGel.ItemGel.class, gel.getUnlocalizedName());

        GelRegistry.getInstance().addGel(new GelPropolsion());
        GelRegistry.getInstance().addGel(new GelReplusion());
    }
}
