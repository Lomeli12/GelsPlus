package net.lomeli.gels.core.handler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.gel.GelRegistry;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GelEffectHandler {
    @SubscribeEvent
    public void livingEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving != null) {
            if (GelRegistry.getInstance().coloredList().containsKey(event.entityLiving.getEntityId())) {

                GelAbility gel = GelRegistry.getInstance().getGel(
                        GelRegistry.getInstance().coloredList().get(event.entityLiving.getEntityId()));
                if (gel != null) {
                    if (event.entityLiving.isWet()) {
                        event.entityLiving.getEntityData().removeTag("gelEffect");
                        GelRegistry.getInstance().removeEntity(event.entityLiving);
                        return;
                    }

                    boolean doEffect = true;
                    if (event.entityLiving instanceof EntityPlayer)
                        doEffect = !((EntityPlayer) event.entityLiving).isSneaking();
                    gel.markedEntityEffect(event.entityLiving.worldObj, event.entityLiving, doEffect);
                }else
                    GelRegistry.getInstance().removeEntity(event.entityLiving);
            }
        }
    }
    
    @SubscribeEvent
    public void checkForEffect(EntityJoinWorldEvent event) {
        if (event.entity != null) {
            if (event.entity instanceof EntityLivingBase) {
                EntityLivingBase entityLiving = (EntityLivingBase)event.entity;
                if (entityLiving.getEntityData().hasKey("gelEffect"))
                    GelRegistry.getInstance().markEntity(entityLiving,
                            entityLiving.getEntityData().getInteger("gelEffect"));
                return;
            }
        }
    }

    @SubscribeEvent
    public void deathEvent(LivingDeathEvent event) {
        if (event.entityLiving != null && GelRegistry.getInstance().coloredList().containsKey(event.entityLiving.getEntityId())) {
            GelRegistry.getInstance().removeEntity(event.entityLiving);
            return;
        }
    }
}
