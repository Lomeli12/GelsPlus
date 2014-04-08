package net.lomeli.gels.network;

import net.minecraft.entity.player.EntityPlayer;

import net.lomeli.gels.GelsPlus;

import net.lomeli.lomlib.network.AbstractPacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketClearList extends AbstractPacket {
    public PacketClearList() {
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        GelsPlus.proxy.getRegistry().coloredList().clear();
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    }

}
