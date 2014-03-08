package net.lomeli.gels.gel;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.world.World;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.core.Strings;

public class GelRepulsion extends GelAbility {

    @Override
    public void gelEffect(World world, int x, int y, int z, int side, Entity entity, boolean doEffect) {
        if (entity != null) {
            switch(side) {
            case 1 :
                entity.fallDistance = 0;
                if (doEffect) {
                    entity.motionY = -1.5;
                    entity.motionX *= 1.5;
                    entity.motionZ *= 1.5;
                }
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
                if (doEffect) {
                    entity.motionY = 1.5;
                    entity.motionX *= 1.5;
                    entity.motionZ *= 1.5;
                }
                break;
            }

            if (doEffect && world.isRemote) {
                world.playSoundAtEntity(entity, Strings.MODID.toLowerCase() + ":repgel", 0.5F,
                        0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
            }
        }
    }

    @Override
    public void gelThrownEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
        gelEffect(world, x, y, z, 0, entity, doEffect);
    }

    @Override
    public Color gelColor() {
        return new Color(0, 70, 255);
    }

    @Override
    public Object[] recipeItems() {
        return new Object[] { Items.slime_ball };
    }

    @Override
    public String gelName() {
        return "gel.GelsPlus:repGel";
    }

    @Override
    public boolean isThrowable() {
        return true;
    }

}
