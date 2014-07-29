package net.lomeli.gels.network;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import net.lomeli.lomlib.network.AbstractPacket;

import net.lomeli.gels.GelsPlus;

public class PacketUpdateRegistry extends AbstractPacket {
    private int entityId, gelEffect;
    private boolean effect;

    public PacketUpdateRegistry() {
    }

    private PacketUpdateRegistry(EntityLivingBase entity, int gelEffect, boolean addRemove) {
        this.entityId = entity.getEntityId();
        this.gelEffect = gelEffect;
        this.effect = addRemove;
    }

    /**
     * Add Entity to registry with effect
     *
     * @param entity
     * @param gel
     */
    public PacketUpdateRegistry(EntityLivingBase entity, int gel) {
        this(entity, gel, true);
    }

    public PacketUpdateRegistry(int entityID, int gel) {
        this.entityId = entityID;
        this.gelEffect = gel;
        this.effect = false;
    }

    /**
     * Remove entity from registry
     *
     * @param entity
     */
    public PacketUpdateRegistry(EntityLivingBase entity) {
        this(entity, 0, false);
    }

    @Override
    public void encodeInto(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.gelEffect);
        buffer.writeBoolean(this.effect);
    }

    @Override
    public void decodeInto(ByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.gelEffect = buffer.readInt();
        this.effect = buffer.readBoolean();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        Entity entity = player.worldObj.getEntityByID(this.entityId);
        setEntity(entity);
    }

    @Override
    public void handleServerSide() {
        Entity entity = MinecraftServer.getServer().getEntityWorld().getEntityByID(this.entityId);
        setEntity(entity);
    }

    public void setEntity(Entity entity) {
        if (entity != null && (entity instanceof EntityLivingBase)) {
            EntityLivingBase entityLiving = (EntityLivingBase) entity;
            if (this.effect) {
                if (!GelsPlus.proxy.getRegistry().coloredList().containsKey(entityLiving.getEntityId())) {
                    entityLiving.getEntityData().setInteger("gelEffect", this.gelEffect);
                    GelsPlus.proxy.getRegistry().markEntity(entityLiving, this.gelEffect);
                }
            } else {
                entityLiving.getEntityData().removeTag("gelEffect");
                GelsPlus.proxy.getRegistry().removeEntity(entityLiving);
            }
        }
    }
}