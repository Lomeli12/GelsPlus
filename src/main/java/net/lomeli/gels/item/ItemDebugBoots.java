package net.lomeli.gels.item;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.core.Strings;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDebugBoots extends ItemArmor {

    public ItemDebugBoots(int id) {
        super(id, EnumHelper.addArmorMaterial("debugboots", -1, new int[] { 0, 0, 0, 0 }, 0), 0, 3);
        this.setCreativeTab(GelsPlus.modTab);
        this.setUnlocalizedName(Strings.MODID + ":debugboots");
    }

    @Override
    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack) {
        if (player != null && itemStack != null) {
            player.fallDistance = 0;
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":longFallBoots");
    }
}
