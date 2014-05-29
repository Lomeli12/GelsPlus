package net.lomeli.gels.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.block.BlockGel;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;
import net.lomeli.gels.core.handler.EventHandler;
import net.lomeli.gels.item.ModItems;
import net.lomeli.gels.network.PacketUpdateRegistry;

import net.lomeli.lomlib.item.ItemUtil;
import net.lomeli.lomlib.network.PacketHandler;

import cpw.mods.fml.common.registry.EntityRegistry;

public class EntityGelThrowable extends EntityThrowable {
    public static ItemStack blockCheck = new ItemStack(Blocks.stone);
    protected int gelBlock;
    private boolean isThrownByBlock;

    public EntityGelThrowable(World world) {
        super(world);
    }

    public EntityGelThrowable(World world, int gel) {
        super(world);
        this.gelBlock = gel;
        this.setSyncBlock();
    }

    public EntityGelThrowable(World world, int gel, boolean block) {
        this(world, gel);
        this.isThrownByBlock = block;
    }

    public EntityGelThrowable(World world, EntityLivingBase entity, int gel) {
        super(world, entity);
        this.gelBlock = gel;
        this.setSyncBlock();
    }

    public EntityGelThrowable(World world, double x, double y, double z, int gel) {
        super(world, x, y, z);
        this.gelBlock = gel;
        this.setSyncBlock();
    }

    public EntityGelThrowable(World world, int gel, float velocity) {
        this(world, gel);
        this.gelBlock = gel;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity, 1.0f);
    }

    public EntityGelThrowable(World world, double x, double y, double z, int gel, float velocity) {
        this(world, x, y, z, gel);
        this.gelBlock = gel;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, velocity, 1.0f);
    }

    public static void init() {
        if (GelsPlus.allowThrowable)
            EntityRegistry.registerModEntity(EntityGelThrowable.class, "gel", EntityRegistry.findGlobalUniqueEntityId(), GelsPlus.instance, 64, 1, true);
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(16, Integer.valueOf(0));
        this.dataWatcher.addObject(17, Integer.valueOf(0));
    }

    public void setSyncBlock() {
        this.dataWatcher.updateObject(16, this.gelBlock);
        this.dataWatcher.updateObject(17, this.isThrownByBlock ? 1 : 0);
    }

    public int getSyncBlock() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }

    @Override
    public void onEntityUpdate() {
        if (this.worldObj.isRemote && this.gelBlock > -1)
            this.gelBlock = this.getSyncBlock();
        super.onEntityUpdate();
    }

    @Override
    protected void onImpact(MovingObjectPosition pos) {
        boolean drops = true;
        int meta = 0;
        int x = pos.blockX;
        int y = pos.blockY;
        int z = pos.blockZ;

        if (pos.entityHit != null) {
            x = (int) pos.entityHit.posX;
            y = (int) pos.entityHit.posY + 1;
            z = (int) pos.entityHit.posZ;

            if (this.gelBlock > -1 && !this.worldObj.isRemote) {
                boolean doEffect = true;
                if (pos.entityHit instanceof EntityPlayer)
                    doEffect = !pos.entityHit.isSneaking();
                if (this.gelBlock < GelsPlus.proxy.getRegistry().getRegistry().size()) {
                    if (!GelsPlus.proxy.getRegistry().getBlackList().contains(pos.entityHit.getClass()) || !GelsPlus.proxy.getRegistry().coloredList().containsKey(pos.entityHit.getEntityId())) {
                        GelAbility gel = null;
                        try {
                            gel = GelsPlus.proxy.getRegistry().getGel(this.gelBlock).newInstance();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (!EventHandler.doesEntityHaveShield(pos.entityHit))
                            gel.gelThrownEffect(this.worldObj, x, y, z, pos.entityHit, doEffect);
                        if ((pos.entityHit instanceof EntityLivingBase) && gel.canColor() && GelsPlus.gelEffects) {
                            PacketHandler.sendEverywhere(GelsPlus.packetChannel.getChannel(), new PacketUpdateRegistry((EntityLivingBase) pos.entityHit, this.gelBlock));
                            drops = false;
                        }
                    }
                }
            }
        }

        if (!this.worldObj.getBlock(x, y, z).getUnlocalizedName().equals(Blocks.snow.getUnlocalizedName())
                || !this.worldObj.getBlock(x, y, z).getUnlocalizedName().equals(Blocks.snow_layer.getUnlocalizedName())) {
            switch(pos.sideHit) {
            case 0 :
                y--;
                meta = 1;
                break;
            case 1 :
                y++;
                meta = 0;
                break;
            case 2 :
                z--;
                meta = 5;
                break;
            case 3 :
                z++;
                meta = 4;
                break;
            case 4 :
                x--;
                meta = 3;
                break;
            case 5 :
                x++;
                meta = 2;
                break;
            }
        }

        if (!this.worldObj.isRemote) {
            if (((getThrower() instanceof EntityPlayer)) && (!((EntityPlayer) getThrower()).canPlayerEdit(x, y, z, pos.sideHit, blockCheck))) {
                if (drops && !this.isThrownByBlock)
                    ItemUtil.dropItemStackIntoWorld(new ItemStack(ModItems.gelBlob, 1, this.gelBlock), this.worldObj, x, y, z, true);
                this.setDead();
                return;
            }

            if (this.worldObj.isAirBlock(x, y, z) && BlockGel.canGelStay(this.worldObj, x, y, z, meta)) {
                if (this.gelBlock > -1) {
                    this.worldObj.setBlock(x, y, z, ModBlocks.gel, this.gelBlock, 3);
                    TileGel tile = (TileGel) worldObj.getTileEntity(x, y, z);
                    if (tile != null) {
                        tile.setSide(meta);
                        if (this.isThrownByBlock)
                            tile.setPickUp(false);
                    }
                    this.worldObj.markBlockForUpdate(x, y, z);
                }
            }else {
                if (drops && !this.isThrownByBlock)
                    ItemUtil.dropItemStackIntoWorld(new ItemStack(ModItems.gelBlob, 1, this.gelBlock), this.worldObj, x, y, z, true);
            }
        }
        this.setDead();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.gelBlock = nbt.getInteger("gelBlock");
        this.isThrownByBlock = nbt.getBoolean("block");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        if (this.gelBlock > -1)
            nbt.setInteger("gelBlock", this.gelBlock);
        nbt.setBoolean("block", this.isThrownByBlock);
    }

}
