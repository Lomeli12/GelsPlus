package net.lomeli.gels.item;

import java.awt.Color;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.lomeli.gels.core.GelRegistry;
import net.lomeli.gels.entity.EntityGelThrowable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGelBlob extends ItemGP {

    public ItemGelBlob() {
        super("gelBlob");
        this.setUnlocalizedName("gelBlob");
        this.setHasSubtypes(true);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < GelRegistry.getInstance().gelColor.size(); i++) {
            list.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode)
            stack.stackSize -= 1;

        Block block = GelRegistry.getInstance().getBlock(stack.getItemDamage());

        if (block != null) {
            world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
            if (!world.isRemote)
                world.spawnEntityInWorld(new EntityGelThrowable(world, player, block));
        }

        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
        return itemStack.getItemDamage() < GelRegistry.getInstance().gelColor.size() ? GelRegistry.getInstance()
                .getColor(itemStack.getItemDamage()).getRGB() : new Color(255, 255, 255).getRGB();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        ItemStack blockstack = null;
        if (stack.getItemDamage() < GelRegistry.getInstance().gelRegistry.size())
            blockstack = new ItemStack(GelRegistry.getInstance().getBlock(stack.getItemDamage()), 1, 1);

        String unlocalizedName = stack.getUnlocalizedName();
        String gelName = blockstack != null ? blockstack.getDisplayName() + " " : "";
        return gelName + StatCollector.translateToLocal(unlocalizedName);
    }
}
