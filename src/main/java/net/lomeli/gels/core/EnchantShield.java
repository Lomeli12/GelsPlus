package net.lomeli.gels.core;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class EnchantShield extends Enchantment {

    public static int enchantID = 90;

    public static EnchantShield enchantShield = new EnchantShield(enchantID);

    protected EnchantShield(int par1) {
        super(par1, 1, EnumEnchantmentType.armor);
        this.setName(Strings.MODID + ":gelShield");
    }

}
