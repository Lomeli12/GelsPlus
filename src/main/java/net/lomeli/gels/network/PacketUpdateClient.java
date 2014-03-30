package net.lomeli.gels.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

import net.lomeli.lomlib.network.AbstractPacket;
import net.lomeli.lomlib.util.ByteUtil;

import net.lomeli.gels.gel.GelRegistry;

public class PacketUpdateClient extends AbstractPacket {
    private HashMap<Integer, Integer> map;

    public PacketUpdateClient() {
    }

    public PacketUpdateClient(HashMap<Integer, Integer> map) {
        this.map = map;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        ByteUtil.writeIntMap(buffer, map);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.map = ByteUtil.readIntMap(buffer);
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        GelRegistry.INSTANCE().coloredList().clear();
        GelRegistry.INSTANCE().coloredList().putAll(this.map);
    }

    @Override
    public void handleServerSide(EntityPlayer player) {

    }
}
