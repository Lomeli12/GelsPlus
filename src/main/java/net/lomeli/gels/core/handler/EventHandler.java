package net.lomeli.gels.core.handler;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.network.PacketClearList;
import net.lomeli.gels.network.PacketHelper;
import net.lomeli.gels.network.PacketUpdateClient;
import net.lomeli.gels.network.PacketUpdateRegistry;

import net.lomeli.lomlib.entity.EntityUtil;
import net.lomeli.lomlib.util.ToolTipUtil;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class EventHandler {

    @SubscribeEvent
    public void livingEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving != null) {
            EntityLivingBase entity = event.entityLiving;
            if (GelsPlus.gelEffects) {
                if (entity.getEntityData().hasKey("gelEffect")) {
                    if (!GelsPlus.proxy.getRegistry().coloredList().containsKey(entity.getEntityId()))
                        PacketHelper.sendEverywhere(new PacketUpdateRegistry(entity, entity.getEntityData().getInteger("gelEffect")));
                }
                if (GelsPlus.proxy.getRegistry().coloredList().containsKey(entity.getEntityId())) {
                    if (entity.isWet()) {
                        PacketHelper.sendEverywhere(new PacketUpdateRegistry(entity));
                        return;
                    }
                    GelAbility gel = GelsPlus.proxy.getRegistry().getGel(GelsPlus.proxy.getRegistry().coloredList().get(entity.getEntityId()));
                    if (gel != null) {
                        boolean doEffect = ((entity instanceof EntityPlayer) ? !((EntityPlayer) entity).isSneaking() : true);
                        gel.markedEntityEffect(entity.worldObj, entity, doEffect);
                    }else
                        PacketHelper.sendEverywhere(new PacketUpdateRegistry(entity));
                }
            }
        }
    }

    @SubscribeEvent
    public void deathEvent(LivingDeathEvent event) {
        if (event.entityLiving != null && GelsPlus.proxy.getRegistry().coloredList().containsKey(event.entityLiving.getEntityId()))
            PacketHelper.sendEverywhere(new PacketUpdateRegistry(event.entityLiving));
    }

    @SubscribeEvent
    public void onSpawnEvent(LivingSpawnEvent event) {
        if (event.entityLiving != null && (event.entityLiving instanceof EntityPig)) {
            if (event.entityLiving.worldObj.rand.nextInt(10000) == 5)
                ((EntityPig) event.entityLiving).setCustomNameTag("hipster");
        }
    }

    @SubscribeEvent
    public void checkForEffect(EntityJoinWorldEvent event) {
        if (event.entity != null && GelsPlus.gelEffects) {
            if (event.entity instanceof EntityLivingBase) {
                EntityLivingBase entityLiving = (EntityLivingBase) event.entity;
                if (entityLiving.getEntityData().hasKey("gelEffect")) {
                    PacketHelper.sendEverywhere(new PacketUpdateRegistry(entityLiving, entityLiving.getEntityData().getInteger("gelEffect")));
                }
            }
            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;
                if (!GelsPlus.checked && !GelsPlus.updater.isUpdate()) {
                    if (FMLCommonHandler.instance().getSide().isClient()) {
                        if (FMLClientHandler.instance().getClient().currentScreen != null) {
                            EntityUtil.sendToChat(player,
                                    ToolTipUtil.BLUE + "[" + ToolTipUtil.ORANGE + Strings.NAME + ToolTipUtil.BLUE + "]: There is a new version available at " + GelsPlus.updater.getDownloadURL());
                            GelsPlus.checked = true;
                        }
                    }
                }
            }
        }
    }

    public static class FMLEvents {
        @SubscribeEvent
        public void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
            PacketHelper.sendServer(new PacketUpdateClient(event.player.getCommandSenderName()));
        }

        @SubscribeEvent
        public void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
            PacketHelper.sendToClient(new PacketClearList(), event.player);
        }
    }

}
