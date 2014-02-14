package net.lomeli.gels.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityAdhesiveGel extends EntityHanging {

    public EntityAdhesiveGel(World p_i1589_1_, int p_i1589_2_, int p_i1589_3_, int p_i1589_4_, int p_i1589_5_) {
        super(p_i1589_1_, p_i1589_2_, p_i1589_3_, p_i1589_4_, p_i1589_5_);
    }

    @Override
    public boolean attackEntityFrom(DamageSource paramDamageSource, float paramFloat) {
        return false;
    }

    @Override
    public boolean interactFirst(EntityPlayer player) {
        if(player != null) {
            ItemStack stack = player.getCurrentEquippedItem();
            if(stack.getItem().getUnlocalizedName().equals(Items.bucket.getUnlocalizedName())) {
                if(!player.capabilities.isCreativeMode)
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
                this.setDead();
            }
        }
        return false;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        if(player != null) {
            player.fallDistance = 0;
            if(player.motionY < -0.06D)
                player.motionY += 0.16D;
        }
    }

    @Override
    public void applyEntityCollision(Entity entity) {
        if(entity instanceof EntityLivingBase) {
            entity.fallDistance = 0;
            if(entity.motionY < -0.06D)
                entity.motionY += 0.16D;
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public int getWidthPixels() {
        return 16;
    }

    @Override
    public int getHeightPixels() {
        return 16;
    }

    @Override
    public void onBroken(Entity paramEntity) {
    }

}
