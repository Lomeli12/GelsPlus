package net.lomeli.gels.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

import net.lomeli.gels.core.Strings;

public class GPPacketHandler implements IPacketHandler {

    private static final byte addEntityPacket = 10, removeEntityPacket = 11;

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
        byte dataId = data.readByte();

        switch(dataId) {
        case addEntityPacket :
            receiveAddPacket(data);
            break;
        case removeEntityPacket :
            receiveRemovePacket(data);
            break;
        }
    }

    public void receiveAddPacket(ByteArrayDataInput data) {
        int entityID = data.readInt(), gelEffect = data.readInt();
        MinecraftServer server = MinecraftServer.getServer();
        Entity entity = server.getEntityWorld().getEntityByID(entityID);
        if (entity != null && entity instanceof EntityLivingBase)
            entity.getEntityData().setInteger("gelEffect", gelEffect);
    }

    public void receiveRemovePacket(ByteArrayDataInput data) {
        int entityID = data.readInt();
        MinecraftServer server = MinecraftServer.getServer();
        Entity entity = server.getEntityWorld().getEntityByID(entityID);
        if (entity != null && entity instanceof EntityLivingBase)
            entity.getEntityData().removeTag("gelEffect");
    }

    public static void addEntityPacket(int entityID, int gelEffect) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            Packet250CustomPayload packet = new Packet250CustomPayload();
            dos.writeByte(addEntityPacket);
            dos.writeInt(entityID);
            dos.writeInt(gelEffect);
            dos.close();
            packet.channel = Strings.PACKET;
            packet.data = baos.toByteArray();
            packet.length = baos.size();
            packet.isChunkDataPacket = false;
            PacketDispatcher.sendPacketToServer(packet);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeEntityPacket(int entityID) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            Packet250CustomPayload packet = new Packet250CustomPayload();
            dos.writeByte(removeEntityPacket);
            dos.writeInt(entityID);
            dos.close();
            packet.channel = Strings.PACKET;
            packet.data = baos.toByteArray();
            packet.length = baos.size();
            packet.isChunkDataPacket = false;
            PacketDispatcher.sendPacketToServer(packet);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
