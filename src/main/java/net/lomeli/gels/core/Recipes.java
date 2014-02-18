package net.lomeli.gels.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.gels.item.ModItems;

public class Recipes {

    public static void loadRecipes() {
        for (int i = 0; i < GelRegistry.getInstance().gelRegistry.size(); i++) {
            addRecipe(new ItemStack(ModItems.gelBlob, 1, i), true, Item.slimeBall, "dyeRed", "dyeGreen", "dyeBlue", GelRegistry
                    .getInstance().getRecipeItems(i));
            addRecipe(new ItemStack(ModItems.gelBucket, 1, i), true, new ItemStack(ModItems.gelBlob, 1, i), Item.bucketEmpty,
                    new ItemStack(Item.potion, 1, 0));
        }
    }

    private static void addRecipe(ItemStack out, boolean shapeless, Object... input) {
        if (shapeless)
            GameRegistry.addRecipe(new ShapelessOreRecipe(out, input));
        else
            GameRegistry.addRecipe(new ShapedOreRecipe(out, !shapeless, input));
    }
}
