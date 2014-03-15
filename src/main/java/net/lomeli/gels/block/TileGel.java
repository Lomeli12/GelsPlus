package net.lomeli.gels.block;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.gel.GelRegistry;

public class TileGel extends TileEntity implements IGel {
    private int side;
    private boolean canBePicked;

    public TileGel(int side) {
        this.side = side;
        this.canBePicked = true;
    }

    public TileGel() {
        this(0);
        this.canBePicked = true;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public boolean canPickUp() {
        return this.canBePicked;
    }

    public void setPickUp(boolean bool) {
        this.canBePicked = bool;
    }

    public GelAbility getAbility(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return meta < GelRegistry.INSTANCE().getRegistry().size() ? GelRegistry.INSTANCE()
                .getGel(world.getBlockMetadata(x, y, z)) : GelRegistry.INSTANCE().getGel(0);
    }

    @Override
    public void doGelEffect(World world, int x, int y, int z, Entity entity, boolean doEffect) {
        if (world.getBlockMetadata(x, y, z) < GelRegistry.INSTANCE().getRegistry().size()) {
            GelAbility gel = GelRegistry.INSTANCE().getGel(world.getBlockMetadata(x, y, z));

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
        tag.setBoolean("canBePicked", this.canBePicked);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        this.side = tag.getInteger("side");
        this.canBePicked = tag.getBoolean("canBePicked");
    }

    @Override
    public Packet getDescriptionPacket() {
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound dataTag = packet != null ? packet.data : new NBTTagCompound();
        writeTag(dataTag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, dataTag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt != null ? pkt.data : new NBTTagCompound();
        readNBT(tag);
    }

}
