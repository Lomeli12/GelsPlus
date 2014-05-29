package net.lomeli.gels.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.item.ModItems;

import net.lomeli.lomlib.recipes.AnvilRecipeManager;
import net.lomeli.lomlib.recipes.FluidAnvilRecipe;
import net.lomeli.lomlib.recipes.ShapedFluidRecipe;
import net.lomeli.lomlib.recipes.ShapelessFluidRecipe;
import net.lomeli.lomlib.util.EnchantmentUtil;

import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
    public static void loadRecipes() {
        addRecipe(new ItemStack(ModItems.blob, 2), true, Items.paper, "fluid$water", "dyeRed", "dyeGreen", "dyeBlue");
        addRecipe(new ItemStack(ModBlocks.gelDispenser), false, "IRI", "GBG", "IWI", 'I', Items.iron_ingot, 'R', Items.redstone, 'G', Blocks.glass_pane, 'B', ModItems.blob, 'W', "fluid$water");
        addRecipe(new ItemStack(ModItems.gelShield), false, "GIG", "IBI", "WIW", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'B', ModItems.blob, 'W', new ItemStack(Blocks.wool, 1,
                OreDictionary.WILDCARD_VALUE));
        for (int i = 0; i < GelsPlus.proxy.getRegistry().getRegistry().size(); i++) {
            GelAbility gel = null;
            try {
                gel = GelsPlus.proxy.getRegistry().getGel(i).newInstance();
            }catch (Exception e) {
                e.printStackTrace();
            }
            if (gel != null) {
                if (gel.recipeItems() != null) {
                    Object[] newInputs = new Object[gel.recipeItems().length + 1];
                    for (int j = 0; j < newInputs.length; j++) {
                        if (j == newInputs.length - 1)
                            newInputs[j] = ModItems.blob;
                        else
                            newInputs[j] = gel.recipeItems()[j];
                    }
                    addRecipe(new ItemStack(ModItems.gelBlob, 3, i), true, newInputs);
                }
                addRecipe(new ItemStack(ModItems.gelBucket, 1, i), true, new ItemStack(ModItems.gelBlob, 1, i), Items.bucket, "fluid$water");
            }
        }
        addAnvil(EnchantmentUtil.getEnchantedBook(EnchantShield.enchantShield, (short) 1), Items.book, ModItems.gelShield, 2);
    }

    private static void addAnvil(ItemStack out, Object left, Object right, int cost) {
        AnvilRecipeManager.addRecipe(new FluidAnvilRecipe(out, left, right, cost));
    }

    private static void addRecipe(ItemStack out, boolean shapeless, Object... input) {
        if (shapeless)
            GameRegistry.addRecipe(new ShapelessFluidRecipe(out, input));
        else
            GameRegistry.addRecipe(new ShapedFluidRecipe(out, !shapeless, input));
    }
}
