package net.lomeli.gels.block;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;

import net.lomeli.gels.core.ModIds;
import net.lomeli.gels.gel.GelAdhesion;
import net.lomeli.gels.gel.GelPropulsion;
import net.lomeli.gels.gel.GelRegistry;
import net.lomeli.gels.gel.GelRepulsion;

public class ModBlocks {
    public static int gelRenderID;
    public static Block gel;

    public static void loadBlocks() {
        gel = new BlockGel(ModIds.gelBlock).setUnlocalizedName("gel");
        GameRegistry.registerBlock(gel, BlockGel.ItemGel.class, gel.getUnlocalizedName());
        
        GelRegistry.INSTANCE().addGel(new GelPropulsion());
        GelRegistry.INSTANCE().addGel(new GelRepulsion());
        GelRegistry.INSTANCE().addGel(new GelAdhesion());
    }
}
