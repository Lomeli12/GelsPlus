package net.lomeli.gels.network;

import net.minecraft.entity.player.EntityPlayer;

import net.lomeli.gels.GelsPlus;

import net.lomeli.lomlib.network.AbstractPacket;

import io.netty.buffer.ByteBuf;

public class PacketClearList extends AbstractPacket {
    public PacketClearList() {
    }

    @Override
    public void encodeInto(ByteBuf buffer) {
    }

    @Override
    public void decodeInto(ByteBuf buffer) {
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        GelsPlus.proxy.getRegistry().coloredList().clear();
    }

    @Override
    public void handleServerSide() {
    }

}
