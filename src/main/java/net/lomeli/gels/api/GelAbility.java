package net.lomeli.gels.api;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class GelAbility {
    public static IGelRegistry gelRegistry;

    public abstract void gelEffect(World world, int x, int y, int z, int side, Entity entity, boolean doEffect);

    public abstract Color gelColor();

    public abstract Object[] recipeItems();

    public abstract String gelName();

    public abstract boolean isThrowable();
}
