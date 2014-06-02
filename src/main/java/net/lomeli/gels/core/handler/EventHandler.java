package net.lomeli.gels.core.handler;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;
import net.lomeli.gels.core.EnchantShield;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.item.ItemShield;
import net.lomeli.gels.item.ModItems;
import net.lomeli.gels.network.PacketClearList;
import net.lomeli.gels.network.PacketUpdateClient;
import net.lomeli.gels.network.PacketUpdateRegistry;
import net.lomeli.gels.network.PacketUtil;

import net.lomeli.lomlib.entity.EntityUtil;
import net.lomeli.lomlib.util.EnchantmentUtil;
import net.lomeli.lomlib.util.ToolTipUtil;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class EventHandler {
    public boolean spawn;

    public EventHandler() {
        spawn = false;
    }
    
    @SubscribeEvent
    public void bucketFillEvent(FillBucketEvent event) {
        ItemStack result = fillCustomBucket(event.world, event.target);
        if (result == null)
            return;
        event.result = result;
        event.setResult(Result.ALLOW);
    }
    
    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
        Block blk = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
        if (blk == ModBlocks.gel) {
            TileEntity tile = world.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
            if (tile != null && tile instanceof TileGel) {
                ItemStack bucket = new ItemStack(ModItems.gelBucket, 1, world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ));
                world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
                return bucket;
            }
        }
        return null;
    }

    @SubscribeEvent
    public void livingEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.entityLiving != null) {
            EntityLivingBase entity = event.entityLiving;
            if (GelsPlus.gelEffects) {
                if (entity.getEntityData().hasKey("gelEffect")) {
                    if (!GelsPlus.proxy.getRegistry().coloredList().containsKey(entity.getEntityId()))
                        PacketUtil.sendEverywhere(new PacketUpdateRegistry(entity, entity.getEntityData().getInteger("gelEffect")));
                }
                if (GelsPlus.proxy.getRegistry().coloredList().containsKey(entity.getEntityId())) {

                    boolean checkForShield = doesEntityHaveShield(entity);

                    if (entity.isWet()) {
                        PacketUtil.sendEverywhere(new PacketUpdateRegistry(entity));
                        return;
                    }
                    GelAbility gel = null;
                    try {
                        gel = GelsPlus.proxy.getRegistry().getGel(GelsPlus.proxy.getRegistry().coloredList().get(entity.getEntityId())).newInstance();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (gel != null && !checkForShield) {
                        boolean doEffect = ((entity instanceof EntityPlayer) ? !((EntityPlayer) entity).isSneaking() : true);
                        gel.markedEntityEffect(entity.worldObj, entity, doEffect);
                    }else
                        PacketUtil.sendEverywhere(new PacketUpdateRegistry(entity));
                }
            }
        }
    }

    public static boolean doesEntityHaveShield(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack != null) {
                    if (stack.getItem() instanceof ItemShield)
                        return true;
                }
            }
            for (int i = 0; i < player.inventory.armorInventory.length; i++) {
                ItemStack armor = player.inventory.armorItemInSlot(i);
                if (armor != null) {
                    if (EnchantmentUtil.itemHasEnchant(armor, EnchantShield.enchantShield))
                        return true;
                }
            }
        }else if (entity instanceof EntityLiving) {
            EntityLiving entityLiving = (EntityLiving) entity;
            ItemStack tool = entityLiving.getEquipmentInSlot(0);
            if (tool != null && tool.getItem() instanceof ItemShield)
                return true;
            else {
                for (int i = 1; i < entityLiving.getLastActiveItems().length; i++) {
                    ItemStack stack = entityLiving.getEquipmentInSlot(i);
                    if (stack != null) {
                        if (stack.getItem() instanceof ItemArmor) {
                            if (EnchantmentUtil.itemHasEnchant(stack, EnchantShield.enchantShield))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public void deathEvent(LivingDeathEvent event) {
        if (event.entityLiving != null && GelsPlus.proxy.getRegistry().coloredList().containsKey(event.entityLiving.getEntityId()))
            PacketUtil.sendEverywhere(new PacketUpdateRegistry(event.entityLiving));
    }

    @SubscribeEvent
    public void onSpawnEvent(LivingSpawnEvent event) {
        EntityPlayer player = event.world.getPlayerEntityByName(Strings.USER);
        if (player != null) {
            if (event.entityLiving != null && (event.entityLiving instanceof EntityPig)) {
                if (event.entityLiving.worldObj.rand.nextInt(10000) == 5)
                    ((EntityPig) event.entityLiving).setCustomNameTag("hipster");
            }
        }
    }

    @SubscribeEvent
    public void checkForEffect(EntityJoinWorldEvent event) {
        if (event.entity != null && GelsPlus.gelEffects) {
            if (event.entity instanceof EntityLivingBase) {
                EntityLivingBase entityLiving = (EntityLivingBase) event.entity;
                if (entityLiving.getEntityData().hasKey("gelEffect")) {
                    PacketUtil.sendEverywhere(new PacketUpdateRegistry(entityLiving, entityLiving.getEntityData().getInteger("gelEffect")));
                }
            }
            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;
                if (!GelsPlus.checked && !GelsPlus.updater.isUpdate()) {
                    if (FMLCommonHandler.instance().getSide().isClient()) {
                        if (FMLClientHandler.instance().getClient().currentScreen != null) {
                            EntityUtil.sendToChat(player,
                                    ToolTipUtil.BLUE + "[" + ToolTipUtil.ORANGE + Strings.NAME + ToolTipUtil.BLUE + "]: There is a new version available at " + GelsPlus.updater.getDownloadURL());
                            GelsPlus.checked = true;
                        }
                    }
                }
            }
        }
    }

    public static class FMLEvents {
        @SubscribeEvent
        public void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
            PacketUtil.sendToServer(new PacketUpdateClient(event.player.getCommandSenderName()));
        }

        @SubscribeEvent
        public void onPlayerLogOut(PlayerEvent.PlayerLoggedOutEvent event) {
            PacketUtil.sendTo(new PacketClearList(), event.player);
        }

    }
}
