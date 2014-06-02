package net.lomeli.gels.network;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import net.lomeli.gels.GelsPlus;

import net.lomeli.lomlib.network.AbstractPacket;
import net.lomeli.lomlib.util.ByteUtil;

import io.netty.buffer.ByteBuf;

import cpw.mods.fml.common.network.ByteBufUtils;

public class PacketUpdateClient extends AbstractPacket {
    private HashMap<Integer, Integer> map;
    private String playerName;

    public PacketUpdateClient() {
    }
    
    private PacketUpdateClient(HashMap<Integer, Integer> map, String name) {
        this.map = map;
        this.playerName = name;
    }

    public PacketUpdateClient(HashMap<Integer, Integer> map) {
        this(map, "");
    }

    public PacketUpdateClient(String playerName) {
        this(new HashMap<Integer, Integer>(), playerName);
    }

    @Override
    public void encodeInto(ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, this.playerName);
        ByteUtil.writeIntMap(buffer, map);
    }

    @Override
    public void decodeInto(ByteBuf buffer) {
        this.playerName = ByteBufUtils.readUTF8String(buffer);
        this.map = ByteUtil.readIntMap(buffer);
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        GelsPlus.proxy.getRegistry().coloredList().clear();
        GelsPlus.proxy.getRegistry().coloredList().putAll(this.map);
    }

    @Override
    public void handleServerSide() {
        EntityPlayer ply = MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(playerName);
        if (ply != null)
            PacketUtil.sendTo(new PacketUpdateClient(GelsPlus.proxy.getRegistry().coloredList()), ply);
    }
} 
