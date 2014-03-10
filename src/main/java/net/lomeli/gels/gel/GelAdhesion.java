package net.lomeli.gels.gel;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.world.World;

import net.lomeli.gels.api.GelAbility;

public class GelAdhesion extends GelAbility{

    @Override
    public void gelEffect(World world, int x, int y, int z, int side, Entity entity, boolean doEffect) {
        if (doEffect) {
            //TODO better way of letting players walk on walls?
            entity.motionY = 0.0001D;
            entity.fallDistance = 0;
        }
    }

    @Override
    public void gelThrownEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
    }

    @Override
    public void markedEntityEffect(World world, EntityLivingBase entity, boolean doEffect) {
    }

    @Override
    public Color gelColor() {
        return new Color(170, 0, 140);
    }

    @Override
    public Object[] recipeItems() {
        return new Object[] { Blocks.web, Items.slime_ball };
    }

    @Override
    public String gelName() {
        return "gel.GelsPlus:adhGel";
    }

    @Override
    public boolean isThrowable() {
        return true;
    }

    @Override
    public boolean canColor() {
        return false;
    }
}
