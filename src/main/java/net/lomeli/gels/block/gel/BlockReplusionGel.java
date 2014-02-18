package net.lomeli.gels.block.gel;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class BlockReplusionGel extends BlockGel {

    public BlockReplusionGel() {
        this.blockTexture = "gel_1";
    }

    @Override
    public void doGelEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
        if (entity != null) {
            int meta = world.getBlockMetadata(x, y, z);
            switch(meta) {
            case 1 :
                entity.fallDistance = 0;
                if (doEffect)
                    entity.motionY = -1.5;
                break;
            case 2 :
                entity.fallDistance = 0;
                if (doEffect)
                    entity.motionX = 2.5;
                break;
            case 3 :
                entity.fallDistance = 0;
                if (doEffect)
                    entity.motionX = -2.5;
                break;
            case 4 :
                entity.fallDistance = 0;
                if (doEffect)
                    entity.motionZ = 2.5;
                break;
            case 5 :
                entity.fallDistance = 0;
                if (doEffect)
                    entity.motionZ = -2.5;
                break;
            default:
                entity.fallDistance = 0;
                if (doEffect)
                    entity.motionY = 1.5;
                break;
            }
        }
    }

    @Override
    public int getGelID() {
        return 1;
    }
}
