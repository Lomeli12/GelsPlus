package net.lomeli.gels.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumHelper;

import net.lomeli.gels.GelsPlus;

public class ItemDebugBoots extends ItemArmor {

    public ItemDebugBoots(int id) {
        super(id, EnumHelper.addArmorMaterial("debugboots", -1, new int[] { 0, 0, 0, 0 }, 0), 0, 3);
        this.setCreativeTab(GelsPlus.modTab);
        this.setUnlocalizedName("debugboots");
    }

    @Override
    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack) {
        if (player != null && itemStack != null) {
            player.fallDistance = 0;
            player.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 0));
        }
    }
}
