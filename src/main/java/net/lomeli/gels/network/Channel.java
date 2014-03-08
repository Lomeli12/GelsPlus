package net.lomeli.gels.network;

import java.util.EnumMap;

import net.lomeli.lomlib.libs.Strings;
import net.lomeli.lomlib.network.AbstractPacket;
import net.lomeli.lomlib.network.ChannelHandler;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.relauncher.Side;

public class Channel extends ChannelHandler {
    public Channel(Class<? extends AbstractPacket>... packetTypes) {
        super(Strings.MOD_ID, packetTypes);
    }
    
    public EnumMap<Side, FMLEmbeddedChannel> getChannel() {
        return this.channels;
    }
}
