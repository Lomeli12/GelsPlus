package net.lomeli.gels.core;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.block.TileDispenser;
import net.lomeli.gels.item.ItemGelBlob;

public class BehaviorGel extends BehaviorDefaultDispenseItem {

    private final BehaviorDefaultDispenseItem defaultBehavior;
    final MinecraftServer server;

    public BehaviorGel(MinecraftServer server) {
        this.server = server;
        this.defaultBehavior = new BehaviorDefaultDispenseItem();
    }

    @Override
    public ItemStack dispenseStack(IBlockSource blockSource, ItemStack itemStack) {
        if (itemStack.getItemDamage() > GelsPlus.proxy.getRegistry().getRegistry().size())
            return this.defaultBehavior.dispense(blockSource, itemStack);
        World worldObj = blockSource.getWorld();
        EnumFacing face = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
        int xCoord = blockSource.getXInt(), yCoord = blockSource.getYInt(), zCoord = blockSource.getZInt(), gelType = itemStack.getItemDamage();
        if (itemStack.getItem() instanceof ItemGelBlob) {
            if (TileDispenser.fireGel(worldObj, xCoord, yCoord, zCoord, face.ordinal(), gelType, false))
                itemStack.stackSize--;
        }
        return itemStack;
    }
}
