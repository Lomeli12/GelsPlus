package net.lomeli.gels.core.handler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.lomlib.entity.EntityUtil;
import net.lomeli.lomlib.util.ToolTipUtil;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.gel.GelRegistry;

public class GelEffectHandler {
    @SubscribeEvent
    public void livingEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving != null && GelsPlus.gelEffects) {
            if (GelRegistry.INSTANCE().coloredList().containsKey(event.entityLiving.getEntityId())) {
                if (event.entityLiving.isWet()) {
                    event.entityLiving.getEntityData().removeTag("gelEffect");
                    GelRegistry.INSTANCE().removeEntity(event.entityLiving);
                    return;
                }
                GelAbility gel = GelRegistry.INSTANCE().getGel(
                        GelRegistry.INSTANCE().coloredList().get(event.entityLiving.getEntityId()));
                if (gel != null) {
                    boolean doEffect = true;
                    if (event.entityLiving instanceof EntityPlayer)
                        doEffect = !event.entityLiving.isSneaking();
                    gel.markedEntityEffect(event.entityLiving.worldObj, event.entityLiving, doEffect);
                } else
                    GelRegistry.INSTANCE().removeEntity(event.entityLiving);
            }
        }
    }

    @SubscribeEvent
    public void checkForEffect(EntityJoinWorldEvent event) {
        if (event.entity != null && GelsPlus.gelEffects) {
            if (event.entity instanceof EntityLivingBase) {
                EntityLivingBase entityLiving = (EntityLivingBase) event.entity;
                if (entityLiving.getEntityData().hasKey("gelEffect"))
                    GelRegistry.INSTANCE().markEntity(entityLiving, entityLiving.getEntityData().getInteger("gelEffect"));
            }
            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;
                if (!GelsPlus.checked && !GelsPlus.updater.isUpdate()) {
                    if (FMLCommonHandler.instance().getSide().isClient()) {
                        if (FMLClientHandler.instance().getClient().currentScreen != null) {
                            EntityUtil.sendToChat(player, ToolTipUtil.BLUE + "[" + ToolTipUtil.ORANGE + Strings.NAME + ToolTipUtil.BLUE + "]: There is a new version available at " + GelsPlus.updater.getDownloadURL());
                            GelsPlus.checked = true;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void deathEvent(LivingDeathEvent event) {
        if (event.entityLiving != null && GelRegistry.INSTANCE().coloredList().containsKey(event.entityLiving.getEntityId())) {
            GelRegistry.INSTANCE().removeEntity(event.entityLiving);
        }
    }
}
