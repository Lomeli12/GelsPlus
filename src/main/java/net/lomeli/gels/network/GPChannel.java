package net.lomeli.gels.network;

import net.lomeli.gels.core.Strings;

import net.lomeli.lomlib.network.AbstractPacket;
import net.lomeli.lomlib.network.ChannelHandler;

public class GPChannel extends ChannelHandler {
    @SafeVarargs
    public GPChannel(Class<? extends AbstractPacket>... packetTypes) {
        super(Strings.MODID, packetTypes);
    }
    
}
