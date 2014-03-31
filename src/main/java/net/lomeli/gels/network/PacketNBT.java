package net.lomeli.gels.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import net.lomeli.lomlib.network.AbstractPacket;

public class PacketNBT extends AbstractPacket {
    private int entity, gelEffect;
    private boolean effect;

    public PacketNBT() {
    }

    public PacketNBT(EntityLivingBase entity, int gelEffect, boolean addRemove) {
        this.entity = entity.getEntityId();
        this.gelEffect = gelEffect;
        this.effect = addRemove;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(this.entity);
        buffer.writeInt(this.gelEffect);
        buffer.writeBoolean(this.effect);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.entity = buffer.readInt();
        this.gelEffect = buffer.readInt();
        this.effect = buffer.readBoolean();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        applyNBT(player);
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        applyNBT(player);
    }

    public void applyNBT(EntityLivingBase entity) {
        if (entity.getEntityId() == this.entity) {
            if (this.effect)
                entity.getEntityData().setInteger("gelEffect", this.gelEffect);
            else
                entity.getEntityData().removeTag("gelEffect");
        } else {
            Entity ent = entity.worldObj.getEntityByID(this.entity);
            if (ent instanceof EntityLivingBase) {
                if (this.effect)
                    ent.getEntityData().setInteger("gelEffect", this.gelEffect);
                else
                    ent.getEntityData().removeTag("gelEffect");
            }
        }
    }
}
