package net.lomeli.gels.item;

import java.awt.Color;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.entity.EntityGelThrowable;

public class ItemGelBlob extends ItemGP {

    public ItemGelBlob() {
        super("gelBlob");
        this.setUnlocalizedName("gelBlobThrowable");
        this.setHasSubtypes(true);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < GelsPlus.proxy.getRegistry().getRegistry().size(); i++) {
            if (GelsPlus.proxy.getRegistry().getGel(i) != null)
                list.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() < GelsPlus.proxy.getRegistry().getRegistry().size() && GelsPlus.allowThrowable) {
            try {
                if (GelsPlus.proxy.getRegistry().getGel(stack.getItemDamage()).newInstance().isThrowable()) {
                    if (!player.capabilities.isCreativeMode)
                        stack.stackSize -= 1;

                    if (stack.getItemDamage() > -1) {
                        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
                        if (!world.isRemote)
                            world.spawnEntityInWorld(new EntityGelThrowable(world, player, stack.getItemDamage()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
        try {
            return itemStack.getItemDamage() < GelsPlus.proxy.getRegistry().getRegistry().size() ? (GelsPlus.proxy.getRegistry().getGel(itemStack.getItemDamage()).newInstance().gelColor() != null ? GelsPlus.proxy
                    .getRegistry().getGel(itemStack.getItemDamage()).newInstance().gelColor().getRGB() : new Color(255, 255, 255).getRGB()) : new Color(255, 255, 255).getRGB();
        } catch (Exception e) {
            e.printStackTrace();
            return Color.WHITE.getRGB();
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalizedName = stack.getUnlocalizedName();
        String gelName = null;
        try {
            gelName = stack.getItemDamage() < GelsPlus.proxy.getRegistry().getRegistry().size() ? GelsPlus.proxy.getRegistry().getGel(stack.getItemDamage()).newInstance().gelName() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StatCollector.translateToLocal(gelName) + " " + StatCollector.translateToLocal(unlocalizedName);
    }
}
