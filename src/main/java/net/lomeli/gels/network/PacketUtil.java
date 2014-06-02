package net.lomeli.gels.network;

import net.minecraft.entity.player.EntityPlayer;

import net.lomeli.gels.GelsPlus;

import net.lomeli.lomlib.network.AbstractPacket;
import net.lomeli.lomlib.network.BasicPacketHandler;

import cpw.mods.fml.common.network.NetworkRegistry;

public class PacketUtil {

    public static void sendToAll(AbstractPacket message) {
        BasicPacketHandler.sendToAll(GelsPlus.packetChannels, message);
    }

    public static void sendTo(AbstractPacket message, EntityPlayer player) {
        BasicPacketHandler.sendTo(GelsPlus.packetChannels, message, player);
    }

    public static void sendToAllAround(AbstractPacket message, NetworkRegistry.TargetPoint point) {
        BasicPacketHandler.sendToAllAround(GelsPlus.packetChannels, message, point);
    }

    public static void sendToDimension(AbstractPacket message, int dimensionId) {
        BasicPacketHandler.sendToDimension(GelsPlus.packetChannels, message, dimensionId);
    }

    public static void sendToServer(AbstractPacket message) {
        BasicPacketHandler.sendToServer(GelsPlus.packetChannels, message);
    }

    public static void sendToAllExcept(AbstractPacket message, EntityPlayer player) {
        BasicPacketHandler.sendToAllExcept(GelsPlus.packetChannels, message, player);
    }

    public static void sendEverywhere(AbstractPacket message) {
        BasicPacketHandler.sendEverywhere(GelsPlus.packetChannels, message);
    }
}
