package net.lomeli.gels.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class GelRegistry {
    public List<Integer> gelRegistry = new ArrayList<Integer>();
    public List<Color> gelColor = new ArrayList<Color>();
    public List<ItemStack> gelRecipeItems = new ArrayList<ItemStack>();
    private static GelRegistry instance;

    public static GelRegistry getInstance() {
        if (instance == null)
            instance = new GelRegistry();
        return instance;
    }

    public int getUniqueID() {
        for (int i = 0; i < gelRegistry.size(); i++) {
            if (gelRegistry.get(i) == null)
                return i;
        }
        return gelRegistry.size();
    }

    public int getBlock(int i) {
        return gelRegistry.get(i);
    }

    public Color getColor(int i) {
        return gelColor.get(i);
    }

    public ItemStack getRecipeItems(int i) {
        return gelRecipeItems.get(i);
    }

    public Color getColorByBlock(Block block) {
        if (gelRegistry.contains(block))
            return gelColor.get(gelRegistry.indexOf(block));
        return null;
    }

    public void addBlock(Integer block, Color color, ItemStack recipeItem) {
        gelRegistry.add(block);
        gelColor.add(color);
        gelRecipeItems.add(recipeItem);
    }

    public void setBlockToSlot(Integer block, Color color, ItemStack recipeItem, int slot) {
        if (slot > 3) {
            gelRegistry.add(slot, block);
            gelColor.add(slot, color);
            gelRecipeItems.add(slot, recipeItem);
        }else
            addBlock(block, color, recipeItem);
    }
}
