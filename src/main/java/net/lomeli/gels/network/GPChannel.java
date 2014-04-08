package net.lomeli.gels.network;

import java.util.EnumMap;

import net.lomeli.gels.core.Strings;

import net.lomeli.lomlib.network.AbstractPacket;
import net.lomeli.lomlib.network.ChannelHandler;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.relauncher.Side;

public class GPChannel extends ChannelHandler {
    @SafeVarargs
    public GPChannel(Class<? extends AbstractPacket>... packetTypes) {
        super(Strings.MODID, packetTypes);
    }

    public EnumMap<Side, FMLEmbeddedChannel> getChannel() {
        return this.channels;
    }
}
