package net.lomeli.gels.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemShield extends ItemGP {

    public ItemShield() {
        super("gelShield");
        this.setMaxStackSize(1);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        String info = StatCollector.translateToLocal("subtext.gelShield");
        list.add(info);
    }

}
