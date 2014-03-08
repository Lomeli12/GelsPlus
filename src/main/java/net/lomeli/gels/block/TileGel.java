package net.lomeli.gels.block;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.gel.GelRegistry;

public class TileGel extends TileEntity implements IGel {
    private int side;

    public TileGel(int side) {
        this.side = side;
    }

    public TileGel() {
        this(0);
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public GelAbility getAbility(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return meta < GelRegistry.getInstance().getRegistry().size() ? GelRegistry.getInstance().getGel(
                world.getBlockMetadata(x, y, z)) : GelRegistry.getInstance().getGel(0);
    }

    @Override
    public void doGelEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
        if (world.getBlockMetadata(x, y, z) < GelRegistry.getInstance().getRegistry().size()) {
            GelAbility gel = GelRegistry.getInstance().getGel(world.getBlockMetadata(x, y, z));

            gel.gelEffect(world, x, y, z, getSide(), entity, doEffect);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeTag(tag);
    }

    public void writeTag(NBTTagCompound tag) {
        tag.setInteger("side", this.side);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        this.side = tag.getInteger("side");
    }

    @Override
    public Packet getDescriptionPacket() {
        S35PacketUpdateTileEntity packet = (S35PacketUpdateTileEntity) super.getDescriptionPacket();
        NBTTagCompound dataTag = packet != null ? packet.func_148857_g() : new NBTTagCompound();
        writeTag(dataTag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, dataTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt != null ? pkt.func_148857_g() : new NBTTagCompound();
        readNBT(tag);
    }

}
