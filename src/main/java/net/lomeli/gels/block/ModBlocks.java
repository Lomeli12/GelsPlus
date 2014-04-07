package net.lomeli.gels.block;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.gels.gel.GelAdhesion;
import net.lomeli.gels.gel.GelPropulsion;
import net.lomeli.gels.gel.GelRegistry;
import net.lomeli.gels.gel.GelRepulsion;

public class ModBlocks {
    public static int dispenserRenderID;
    public static Block gel, gelDispenser;

    public static void loadBlocks() {
        gel = new BlockGel().setBlockName("gel");
        GameRegistry.registerBlock(gel, BlockGel.ItemGel.class, gel.getUnlocalizedName());

        gelDispenser = new BlockGelDispenser().setBlockName("dispenser");
        GameRegistry.registerBlock(gelDispenser, gelDispenser.getUnlocalizedName());

        GelRegistry.INSTANCE().addGel(new GelPropulsion());
        GelRegistry.INSTANCE().addGel(new GelRepulsion());
        GelRegistry.INSTANCE().addGel(new GelAdhesion());
    }
}
