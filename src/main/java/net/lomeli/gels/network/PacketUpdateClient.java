package net.lomeli.gels.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.ByteBufUtils;

import net.lomeli.lomlib.network.AbstractPacket;
import net.lomeli.lomlib.network.PacketHandler;
import net.lomeli.lomlib.util.ByteUtil;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.gel.GelRegistry;

public class PacketUpdateClient extends AbstractPacket {
    private HashMap<Integer, Integer> map;
    private String playerName;

    public PacketUpdateClient() {
    }

    public PacketUpdateClient(HashMap<Integer, Integer> map) {
        this.map = map;
    }

    public PacketUpdateClient(String playerName) {
        this.playerName = playerName;
        this.map = new HashMap<Integer, Integer>();
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, this.playerName);
        ByteUtil.writeIntMap(buffer, map);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        this.playerName = ByteBufUtils.readUTF8String(buffer);
        this.map = ByteUtil.readIntMap(buffer);
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        GelRegistry.INSTANCE().coloredList().clear();
        GelRegistry.INSTANCE().coloredList().putAll(this.map);
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        World world = player.worldObj;
        if (world != null) {
            EntityPlayer entityPlayer = world.getPlayerEntityByName(this.playerName);
            if (entityPlayer != null) {
                PacketHandler.sendTo(GelsPlus.packetChannel.getChannel(), new PacketUpdateClient(GelRegistry.INSTANCE().coloredList()), entityPlayer);
            }
        }
    }
}
