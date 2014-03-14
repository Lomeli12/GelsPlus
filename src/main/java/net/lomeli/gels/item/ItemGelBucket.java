package net.lomeli.gels.item;

import java.awt.Color;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.lomeli.gels.block.BlockGel;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.gel.GelRegistry;

public class ItemGelBucket extends ItemGP {
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public ItemGelBucket(int id) {
        super(id, "bucket_");
        setUnlocalizedName("GelBucket");
        setHasSubtypes(true);
        setContainerItem(Item.bucketEmpty);
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + itemTexture + 0);
        iconArray = new Icon[2];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MODID.toLowerCase() + ":" + itemTexture + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public Icon getIcon(ItemStack itemStack, int renderPass) {
        return renderPass == 0 ? iconArray[0] : iconArray[1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
        return (itemStack.getItemDamage() < GelRegistry.INSTANCE().getRegistry().size() && renderPass == 1) ? (GelRegistry
                .INSTANCE().getGel(itemStack.getItemDamage()).gelColor() != null ? GelRegistry.INSTANCE()
                .getGel(itemStack.getItemDamage()).gelColor().getRGB() : new Color(255, 255, 255).getRGB()) : Color.WHITE
                .getRGB();
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
            float hitY, float hitZ) {
        ItemStack returnItem = itemStack;
        if (!player.capabilities.isCreativeMode)
            returnItem = new ItemStack(Item.bucketEmpty);

        int newX = x, newY = y, newZ = z;
        switch(side) {
        case 0 :
            newY--;
            break;
        case 1 :
            newY++;
            break;
        case 2 :
            newZ--;
            break;
        case 3 :
            newZ++;
            break;
        case 4 :
            newX--;
            break;
        case 5 :
            newX++;
            break;
        default:
            newY++;
            break;
        }

        if (!world.isRemote) {
            int newSide = 0;
            switch(side) {
            case 0 :
                newSide = 1;
                break;
            case 1 :
                newSide = 0;
                break;
            case 2 :
                newSide = 5;
                break;
            case 3 :
                newSide = 4;
                break;
            case 4 :
                newSide = 3;
                break;
            case 5 :
                newSide = 2;
                break;
            default:
                newSide = 1;
                break;
            }

            if (world.isAirBlock(newX, newY, newZ) && BlockGel.canGelStay(world, newX, newY, newZ, newSide)) {
                placeBlockAt(itemStack, player, world, newX, newY, newZ, side, hitX, hitY, hitZ, itemStack.getItemDamage());
                world.markBlockForUpdate(newX, newY, newZ);
                world.markBlockForRenderUpdate(newX, newY, newZ);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, returnItem);
            }
        }

        return false;
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
            float hitY, float hitZ, int metadata) {
        if (!world.setBlock(x, y, z, ModBlocks.gel.blockID, metadata, 3)) {
            return false;
        }

        if (world.getBlockId(x, y, z) == ModBlocks.gel.blockID) {
            ModBlocks.gel.onBlockPlacedBy(world, x, y, z, player, stack);
            ModBlocks.gel.onPostBlockPlaced(world, x, y, z, metadata);
            TileGel gel = (TileGel) world.getBlockTileEntity(x, y, z);
            if (gel != null) {
                int newSide;
                switch(side) {
                case 0 :
                    newSide = 1;
                    break;
                case 1 :
                    newSide = 0;
                    break;
                case 2 :
                    newSide = 5;
                    break;
                case 3 :
                    newSide = 4;
                    break;
                case 4 :
                    newSide = 3;
                    break;
                case 5 :
                    newSide = 2;
                    break;
                default:
                    newSide = 1;
                    break;
                }
                gel.setSide(newSide);
            }
            world.markBlockForUpdate(x, y, z);
            world.markBlockForRenderUpdate(x, y, z);
        }
        return true;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
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
    public String getItemStackDisplayName(ItemStack stack) {
        String unlocalizedName = stack.getUnlocalizedName();
        String gelName = stack.getItemDamage() < GelRegistry.INSTANCE().getRegistry().size() ? GelRegistry.INSTANCE()
                .getGel(stack.getItemDamage()).gelName() : "";
        return StatCollector.translateToLocal(gelName) + " " + StatCollector.translateToLocal(unlocalizedName);
    }
}
