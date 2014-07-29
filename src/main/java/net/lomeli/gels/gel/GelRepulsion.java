package net.lomeli.gels.gel;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.world.World;

import net.lomeli.gels.api.GelAbility;

public class GelRepulsion extends GelAbility {
    public static double bounciness;

    @Override
    public void gelEffect(World world, int x, int y, int z, int side, Entity entity, boolean doEffect) {
        if (entity != null) {
            switch (side) {
                case 1:
                    if (doEffect) {
                        entity.motionY = -bounciness;
                        entity.motionX *= bounciness;
                        entity.motionZ *= bounciness;
                    }
                    entity.fallDistance = 0;
                    break;
                case 2:
                    if (doEffect)
                        entity.motionX = bounciness;
                    entity.fallDistance = 0;
                    break;
                case 3:
                    if (doEffect)
                        entity.motionX = -bounciness;
                    entity.fallDistance = 0;
                    break;
                case 4:
                    if (doEffect)
                        entity.motionZ = bounciness;
                    entity.fallDistance = 0;
                    break;
                case 5:
                    if (doEffect)
                        entity.motionZ = -bounciness;
                    entity.fallDistance = 0;
                    break;
                default:
                    double boosted = 0;
                    if (entity.motionY < 0)
                        boosted = entity.motionY / -1D;

                    if (doEffect) {
                        entity.motionY = bounciness + boosted;
                        entity.motionX *= bounciness;
                        entity.motionZ *= bounciness;
                    }
                    entity.fallDistance = 0;
                    break;
            }

            if (doEffect && world.isRemote)
                world.playSoundAtEntity(entity, "gelsplus:repgel", 1.0F, 1.0F);
        }
    }

    @Override
    public void gelThrownEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
        gelEffect(world, x, y, z, 0, entity, doEffect);
    }

    @Override
    public void markedEntityEffect(World world, EntityLivingBase entity, boolean doEffect) {
        if (doEffect) {
            if (entity.onGround) {
                entity.motionY = 1.5f;
                entity.fallDistance = 1.5f;
            }
        }
    }

    @Override
    public Color gelColor() {
        return new Color(0, 70, 255);
    }

    @Override
    public Object[] recipeItems() {
        return new Object[]{Items.slime_ball};
    }

    @Override
    public String gelName() {
        return "gel.GelsPlus:repGel";
    }

    @Override
    public boolean isThrowable() {
        return true;
    }

    @Override
    public boolean canColor() {
        return true;
    }

}
