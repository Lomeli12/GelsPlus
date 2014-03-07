package net.lomeli.gels.block.gel;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public interface IGel {
    public abstract void doGelEffect(World world, int x, int y, int z, Entity entity, boolean doEffect);
}
