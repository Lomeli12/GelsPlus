package net.lomeli.gels.gel;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.world.World;

import net.lomeli.gels.api.GelAbility;

public class GelPropulsion extends GelAbility {

    @Override
    public void gelEffect(World world, int x, int y, int z, int side, Entity entity, boolean doEffect) {
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

    @Override
    public Color gelColor() {
        return new Color(255, 140, 0);
    }

    @Override
    public Object[] recipeItems() {
        return new Object[] { Items.sugar };
    }

    @Override
    public String gelName() {
        return "gel.GelsPlus:proGel";
    }

    @Override
    public boolean isThrowable() {
        return true;
    }

}
