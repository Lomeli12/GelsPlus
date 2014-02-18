package net.lomeli.gels.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.core.Strings;

public class BlockGP extends Block {

    @SideOnly(Side.CLIENT)
    protected String blockTexture;

    public BlockGP(int id, Material p_i45394_1_) {
        super(id, p_i45394_1_);
        this.setCreativeTab(GelsPlus.modTab);
    }

    public BlockGP(int id, Material p_i45394_1_, String texture) {
        super(id, p_i45394_1_);
        this.setCreativeTab(GelsPlus.modTab);
        this.blockTexture = texture;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + this.blockTexture);
    }

    @Override
    public Block setUnlocalizedName(String name) {
        return super.setUnlocalizedName(Strings.MODID + ":" + name);
    }

}
