package net.lomeli.gels.item;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.core.Strings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.common.util.EnumHelper;

public class ItemDebugBoots extends ItemArmor {

    public ItemDebugBoots() {
        super(EnumHelper.addArmorMaterial("debugboots", -1, new int[] { 0, 0, 0, 0 }, 0), 0, 3);
        this.setCreativeTab(GelsPlus.modTab);
        this.setUnlocalizedName(Strings.MODID + ":debugboots");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (player != null && itemStack != null) {
            player.fallDistance = 0;
        }
    }
}
