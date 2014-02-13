package net.lomeli.gels.core;

import net.lomeli.gels.block.IGel;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.item.ModItems;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketHandler {

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {
        ItemStack result = fillCustomBucket(event.world, event.target);

        if(result == null) {
            return;
        }

        event.result = result;
        event.setResult(Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

        int meta = world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);

        Item bucket = Item.getItemFromBlock(block);

        if(bucket != null && block instanceof IGel) {
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            return new ItemStack(ModItems.gelBucket, meta);
        }
        return null;

        //

        /*
         * if(bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY,
         * pos.blockZ) == 0) { world.setBlockToAir(pos.blockX, pos.blockY,
         * pos.blockZ); return new ItemStack(bucket); }else { return null; }
         */
    }
}
