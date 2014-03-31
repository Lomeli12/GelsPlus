package net.lomeli.gels.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import cpw.mods.fml.common.registry.EntityRegistry;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.block.BlockGel;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;
import net.lomeli.gels.gel.GelRegistry;
import net.lomeli.gels.item.ModItems;

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
            EntityRegistry.registerModEntity(EntityGelThrowable.class, "gel", EntityRegistry.findGlobalUniqueEntityId(),
                    GelsPlus.instance, 64, 1, true);
    }

    public static void dropItemStackIntoWorld(ItemStack stack, World world, double x, double y, double z, boolean velocity) {
        if (stack != null) {
            float x2 = 0.5F;
            float y2 = 0.0F;
            float z2 = 0.5F;

            if (velocity) {
                x2 = world.rand.nextFloat() * 0.8F + 0.1F;
                y2 = world.rand.nextFloat() * 0.8F + 0.1F;
                z2 = world.rand.nextFloat() * 0.8F + 0.1F;
            }
            EntityItem entity = new EntityItem(world, x + x2, y + y2, z + z2, stack.copy());

            if (velocity) {
                entity.motionX = ((float) world.rand.nextGaussian() * 0.05F);
                entity.motionY = ((float) world.rand.nextGaussian() * 0.05F + 0.2F);
                entity.motionZ = ((float) world.rand.nextGaussian() * 0.05F);
            } else {
                entity.motionY = -0.0500000007450581D;
                entity.motionX = 0.0D;
                entity.motionZ = 0.0D;
            }

            world.spawnEntityInWorld(entity);
        }
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
                if (this.gelBlock < GelRegistry.INSTANCE().getRegistry().size()) {
                    if (!GelRegistry.INSTANCE().getBlackList().contains(pos.entityHit.getClass())) {
                        GelRegistry.INSTANCE().getGel(this.gelBlock)
                                .gelThrownEffect(this.worldObj, x, y, z, pos.entityHit, doEffect);
                        if ((pos.entityHit instanceof EntityLivingBase) && GelRegistry.INSTANCE().getGel(this.gelBlock).canColor() && GelsPlus.gelEffects) {
                            GelRegistry.INSTANCE().markEntity((EntityLivingBase) pos.entityHit, this.gelBlock);
                            drops = false;
                        }
                    }
                }
            }
        }

        if (!this.worldObj.getBlock(x, y, z).getUnlocalizedName().equals(Blocks.snow.getUnlocalizedName())
                || !this.worldObj.getBlock(x, y, z).getUnlocalizedName().equals(Blocks.snow_layer.getUnlocalizedName())) {
            switch (pos.sideHit) {
                case 0:
                    y--;
                    meta = 1;
                    break;
                case 1:
                    y++;
                    meta = 0;
                    break;
                case 2:
                    z--;
                    meta = 5;
                    break;
                case 3:
                    z++;
                    meta = 4;
                    break;
                case 4:
                    x--;
                    meta = 3;
                    break;
                case 5:
                    x++;
                    meta = 2;
                    break;
            }
        }

        if (!this.worldObj.isRemote) {
            if (((getThrower() instanceof EntityPlayer))
                    && (!((EntityPlayer) getThrower()).canPlayerEdit(x, y, z, pos.sideHit, blockCheck))) {
                if (drops && !this.isThrownByBlock)
                    dropItemStackIntoWorld(new ItemStack(ModItems.gelBlob, 1, this.gelBlock), this.worldObj, x, y, z, true);
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
            } else {
                if (drops && !this.isThrownByBlock)
                    dropItemStackIntoWorld(new ItemStack(ModItems.gelBlob, 1, this.gelBlock), this.worldObj, x, y, z, true);
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
