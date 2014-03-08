package net.lomeli.gels.item;

import java.awt.Color;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.entity.EntityGelThrowable;
import net.lomeli.gels.gel.GelRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemGelBlob extends ItemGP {

    public ItemGelBlob() {
        super("gelBlob");
        this.setUnlocalizedName("gelBlob.name");
        this.setHasSubtypes(true);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < GelRegistry.getInstance().getRegistry().size(); i++) {
            if (GelRegistry.getInstance().getGel(i) != null)
                list.add(new ItemStack(id, 1, i));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() < GelRegistry.getInstance().getRegistry().size() && GelsPlus.allowThrowable) {
            if (GelRegistry.getInstance().getGel(stack.getItemDamage()).isThrowable()) {
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
        return itemStack.getItemDamage() < GelRegistry.getInstance().getRegistry().size() ? (GelRegistry.getInstance()
                .getGel(itemStack.getItemDamage()).gelColor() != null ? GelRegistry.getInstance()
                .getGel(itemStack.getItemDamage()).gelColor().getRGB() : new Color(255, 255, 255).getRGB()) : new Color(255, 255,
                255).getRGB();
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalizedName = stack.getUnlocalizedName();
        String gelName = stack.getItemDamage() < GelRegistry.getInstance().getRegistry().size() ? GelRegistry.getInstance()
                .getGel(stack.getItemDamage()).gelName() : "";
        return StatCollector.translateToLocal(gelName) + " " + StatCollector.translateToLocal(unlocalizedName);
    }
}
