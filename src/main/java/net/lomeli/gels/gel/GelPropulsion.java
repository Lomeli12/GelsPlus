package net.lomeli.gels.gel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import net.lomeli.gels.api.GelAbility;

public class GelPropulsion extends GelAbility {

    public static List<Integer> speedUpList = new ArrayList<Integer>();

    @Override
    public void gelEffect(World world, int x, int y, int z, int side, Entity entity, boolean doEffect) {
        double movementEffect = 0.09D;
        if (doEffect) {
            if (entity.motionX != 0) {
                if (entity.motionX > 0.1D)
                    entity.motionX += movementEffect;
                else if (entity.motionX < -0.1D)
                    entity.motionX -= movementEffect;
            }
            if (entity.motionZ != 0) {
                if (entity.motionZ > 0.1D)
                    entity.motionZ += movementEffect;
                else if (entity.motionZ < -0.1D)
                    entity.motionZ -= movementEffect;
            }
        }
    }

    @Override
    public void gelThrownEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
        if (doEffect) {
            if (entity instanceof EntityLivingBase)
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 500, 0));
        }
    }

    @Override
    public void markedEntityEffect(World world, EntityLivingBase entity, boolean doEffect) {
        if (doEffect)
            entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 2, 1));
    }

    @Override
    public Color gelColor() {
        return new Color(255, 140, 0);
    }

    @Override
    public Object[] recipeItems() {
        return new Object[] { Item.sugar };
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
