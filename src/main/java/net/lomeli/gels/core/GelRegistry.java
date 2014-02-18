package net.lomeli.gels.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;

public class GelRegistry {
    private List<Integer> gelRegistry = new ArrayList<Integer>();
    private List<Color> gelColor = new ArrayList<Color>();
    private static GelRegistry instance;

    public static GelRegistry getInstance() {
        if(instance == null)
            instance = new GelRegistry();
        return instance;
    }

    public int getUniqueID() {
        for(int i = 0; i < gelRegistry.size(); i++) {
            if(gelRegistry.get(i) == null)
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

    public Color getColorByBlock(Block block) {
        if(gelRegistry.contains(block))
            return gelColor.get(gelRegistry.indexOf(block));
        return null;
    }

    public void addBlock(Integer block, Color color) {
        gelRegistry.add(block);
        gelColor.add(color);
    }

    public void setBlockToSlot(Integer block, Color color, int slot) {
        if(slot > 3) {
            gelRegistry.add(slot, block);
            gelColor.add(slot, color);
        }else
            addBlock(block, color);
    }
}
