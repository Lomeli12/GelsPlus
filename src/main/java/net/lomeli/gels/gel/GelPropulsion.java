package net.lomeli.gels.gel;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import net.lomeli.gels.api.GelAbility;

public class GelPropulsion extends GelAbility {
    public static double speedBoost;

    @Override
    public void gelEffect(World world, int x, int y, int z, int side, Entity entity, boolean doEffect) {
        if (doEffect) {
            if (entity instanceof EntityLivingBase) {
                if (entity.onGround) {
                    if (((EntityLivingBase) entity).moveForward > 0f)
                        entity.moveFlying(0f, 1f, 0.15f);
                }
            } else {
                if (entity.motionX != 0) {
                    if (entity.motionX > 0.1D)
                        entity.motionX += speedBoost;
                    else if (entity.motionX < -0.1D)
                        entity.motionX -= speedBoost;
                }
                if (entity.motionZ != 0) {
                    if (entity.motionZ > 0.1D)
                        entity.motionZ += speedBoost;
                    else if (entity.motionZ < -0.1D)
                        entity.motionZ -= speedBoost;
                }
            }
        }

    }

    @Override
    public void gelThrownEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
        if (doEffect) {
            if (entity instanceof EntityLivingBase)
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 500, 0, true));
        }
    }

    @Override
    public void markedEntityEffect(World world, EntityLivingBase entity, boolean doEffect) {
        if (doEffect) {
            if (entity.moveForward > 0f)
                entity.moveFlying(0f, 1f, 0.125f);
        }
    }

    @Override
    public Color gelColor() {
        return new Color(255, 140, 0);
    }

    @Override
    public Object[] recipeItems() {
        return new Object[]{Items.sugar};
    }

    @Override
    public String gelName() {
        return "gel.GelsPlus:proGel";
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
