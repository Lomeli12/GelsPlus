package net.lomeli.gels.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.core.Strings;

public class BlockGP extends Block {

    @SideOnly(Side.CLIENT)
    protected String blockTexture;

    public BlockGP(Material p_i45394_1_) {
        super(p_i45394_1_);
        this.setCreativeTab(GelsPlus.modTab);
    }

    public BlockGP(Material p_i45394_1_, String texture) {
        super(p_i45394_1_);
        this.setCreativeTab(GelsPlus.modTab);
        this.blockTexture = texture;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + this.blockTexture);
    }

}
