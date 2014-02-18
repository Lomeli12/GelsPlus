package net.lomeli.gels.block.gel;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BlockReplusionGel extends BlockGel {

    public BlockReplusionGel(int id) {
        super(id);
        this.blockTexture = "gel_1";
    }

    @Override
    public void doGelEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
        if (entity != null) {
            entity.fallDistance = 0;
            if (doEffect)
                entity.motionY = 1.5;
        }
    }

    @Override
    public int getGelID() {
        return 1;
    }
}
