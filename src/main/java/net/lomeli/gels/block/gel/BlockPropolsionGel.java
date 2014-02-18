package net.lomeli.gels.block.gel;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BlockPropolsionGel extends BlockGel {

    public BlockPropolsionGel(int id) {
        super(id);
        this.blockTexture = "gel_0";
    }

    @Override
    public void doGelEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
        double moveX = 0, moveZ = 0;
        if ((entity.motionX > 0.1D || entity.motionX < -0.1D) && (entity.motionZ > 0.1D || entity.motionZ < -0.1D)) {
            double mov = 0.045D;
            if (entity.motionX > 0.1D)
                moveX = mov;
            else if (entity.motionX < -0.1D)
                moveX = -mov;

            if (entity.motionZ > 0.1D)
                entity.motionZ = mov;
            else if (entity.motionZ < -0.1D)
                entity.motionZ = -mov;
        }else {
            double mov = 0.1D;
            if (entity.motionX > 0.1D)
                moveX = mov;
            else if (entity.motionX < -0.1D)
                moveX = -mov;

            if (entity.motionZ > 0.1D)
                moveZ = mov;
            else if (entity.motionZ < -0.1D)
                moveZ = -mov;
        }

        if (doEffect) {
            entity.motionX += moveX;
            entity.motionZ += moveZ;
        }
    }
}
