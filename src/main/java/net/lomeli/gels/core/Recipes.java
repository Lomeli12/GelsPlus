package net.lomeli.gels.core;

import net.lomeli.gels.item.ModItems;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes {
    public static void loadRecipes() {
        for (int i = 0; i < GelRegistry.getInstance().gelRegistry.size(); i++) {
            addRecipe(new ItemStack(ModItems.gelBlob, 3, i), true, Items.slime_ball, "dyeRed", "dyeGreen", "dyeBlue", GelRegistry
                    .getInstance().getRecipeItems(i), Items.iron_ingot);
            addRecipe(new ItemStack(ModItems.gelBucket, 1, i), true, new ItemStack(ModItems.gelBlob, 1, i), Items.bucket,
                    new ItemStack(Items.potionitem, 1, 0));
        }
    }

    private static void addRecipe(ItemStack out, boolean shapeless, Object... input) {
        if (shapeless)
            GameRegistry.addRecipe(new ShapelessOreRecipe(out, input));
        else
            GameRegistry.addRecipe(new ShapedOreRecipe(out, !shapeless, input));
    }
}
