package net.lomeli.gels.network;

import net.minecraft.entity.player.EntityPlayer;

import net.lomeli.gels.GelsPlus;

import net.lomeli.lomlib.network.AbstractPacket;
import net.lomeli.lomlib.network.PacketHandler;

public class PacketHelper {
    public static void sendAll(AbstractPacket pkt) {
        PacketHandler.sendToAll(GelsPlus.packetChannel.getChannel(), pkt);
    }
    
    public static void sendEverywhere(AbstractPacket pkt) {
        sendAll(pkt);
        sendServer(pkt);
    }
    
    public static void sendToClient(AbstractPacket pkt, EntityPlayer ply) {
        PacketHandler.sendTo(GelsPlus.packetChannel.getChannel(), pkt, ply);
    }
    
    public static void sendServer(AbstractPacket pkt) {
        PacketHandler.sendToServer(GelsPlus.packetChannel.getChannel(), pkt);
    }
}
