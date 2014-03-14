package net.lomeli.gels.gel;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import net.lomeli.gels.api.GelAbility;

public class GelAdhesion extends GelAbility {

    @Override
    public void gelEffect(World world, int x, int y, int z, int side, Entity entity, boolean doEffect) {
        if (doEffect) {
            // TODO better way of letting players walk on walls?
            entity.fallDistance = 0;
            if (side == 0)
                entity.motionY = 0f;
            else
                entity.motionY = 0.08f;
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
        return new Object[] { Block.web, Item.slimeBall };
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
