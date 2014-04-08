package net.lomeli.gels.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.entity.EntityGelThrowable;

public class TileDispenser extends TileEntity {
    private int gelType, orientation, tick;
    private boolean isPowered;

    public TileDispenser(int type, int dir) {
        this.gelType = type;
        this.orientation = dir;
    }

    public TileDispenser() {
        this(0, 0);
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            if (isPowered()) {
                tick++;
            }else
                tick = GelsPlus.ticksBetweenThrow - 1;
        }

        if (this.tick >= GelsPlus.ticksBetweenThrow) {
            tick = 0;
            fireGelBaseOnOrientation();
        }

        this.isPowered = this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
        this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int dir) {
        this.orientation = dir;
    }

    public int getGelType() {
        return this.gelType;
    }

    public void setGelType(int gelType) {
        this.gelType = gelType;
    }

    public boolean isPowered() {
        return this.isPowered;
    }

    public void fireGelBaseOnOrientation() {
        EntityGelThrowable gel = new EntityGelThrowable(this.worldObj, getGelType(), true);
        switch(getOrientation()) {
        case 0 :
            gel.setPosition(xCoord + 0.5, yCoord - 0.2, zCoord + 0.5);
            gel.setThrowableHeading(0, -1D, 0, 1f, 1f);
            break;
        case 2 :
            gel.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord - 0.3);
            gel.setThrowableHeading(0, 0, -1D, 1f, 1f);
            break;
        case 3 :
            gel.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 1.3);
            gel.setThrowableHeading(0, 0, 1D, 1f, 1f);
            break;
        case 4 :
            gel.setPosition(xCoord - 0.1, yCoord + 0.5, zCoord + 0.5);
            gel.setThrowableHeading(-1D, 0, 0, 1f, 1f);
            break;
        case 5 :
            gel.setPosition(xCoord + 1.1, yCoord + 0.5, zCoord + 0.5);
            gel.setThrowableHeading(1D, 0, 0, 1f, 1f);
            break;
        default:
            gel.setPosition(xCoord + 0.5, yCoord + 1, zCoord + 0.5);
            gel.setThrowableHeading(0, 1D, 0, 1f, 1f);
            break;
        }
        if (!this.worldObj.isRemote)
            this.worldObj.spawnEntityInWorld(gel);
        this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.bow", 0.5F, 0.4F / (this.worldObj.rand.nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeTag(tag);
    }

    public void writeTag(NBTTagCompound tag) {
        tag.setInteger("gelType", this.gelType);
        tag.setInteger("orientation", this.orientation);
        tag.setInteger("tick", this.tick);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        this.gelType = tag.getInteger("gelType");
        this.orientation = tag.getInteger("orientation");
        this.tick = tag.getInteger("tick");
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
