package net.lomeli.gels.item;

import java.awt.Color;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.entity.EntityGelThrowable;
import net.lomeli.gels.gel.GelRegistry;

public class ItemGelBlob extends ItemGP {

    public ItemGelBlob(int id) {
        super(id, "gelBlob");
        this.setUnlocalizedName("gelBlobThrowable");
        this.setHasSubtypes(true);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(int id, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < GelRegistry.INSTANCE().getRegistry().size(); i++) {
            if (GelRegistry.INSTANCE().getGel(i) != null)
                list.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() < GelRegistry.INSTANCE().getRegistry().size() && GelsPlus.allowThrowable) {
            if (GelRegistry.INSTANCE().getGel(stack.getItemDamage()).isThrowable()) {
                if (!player.capabilities.isCreativeMode)
                    stack.stackSize -= 1;

                if (stack.getItemDamage() > -1) {
                    world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
                    if (!world.isRemote)
                        world.spawnEntityInWorld(new EntityGelThrowable(world, player, stack.getItemDamage()));
                }
            }
        }
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
        return itemStack.getItemDamage() < GelRegistry.INSTANCE().getRegistry().size() ? (GelRegistry.INSTANCE()
                .getGel(itemStack.getItemDamage()).gelColor() != null ? GelRegistry.INSTANCE().getGel(itemStack.getItemDamage())
                .gelColor().getRGB() : new Color(255, 255, 255).getRGB()) : new Color(255, 255, 255).getRGB();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalizedName = stack.getUnlocalizedName();
        String gelName = stack.getItemDamage() < GelRegistry.INSTANCE().getRegistry().size() ? GelRegistry.INSTANCE()
                .getGel(stack.getItemDamage()).gelName() : "";
        return StatCollector.translateToLocal(gelName) + " " + StatCollector.translateToLocal(unlocalizedName);
    }
}
