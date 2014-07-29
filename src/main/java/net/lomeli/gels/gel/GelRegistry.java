package net.lomeli.gels.gel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.api.IGelRegistry;

public class GelRegistry implements IGelRegistry {
    private List<Class<? extends GelAbility>> gels = new ArrayList<Class<? extends GelAbility>>();
    private List<Class<? extends Entity>> blackList = new ArrayList<Class<? extends Entity>>();
    private List<Block> blockBlackList = new ArrayList<Block>();
    private HashMap<Integer, Integer> coloredEntities = new HashMap<Integer, Integer>();

    public void initRegistry() {
        addGel(GelPropulsion.class);
        addGel(GelRepulsion.class);
        addGel(GelAdhesion.class);
    }

    @Override
    public int getUniqueID() {
        for (int i = 0; i < gels.size(); i++) {
            if (gels.get(i) == null)
                return i;
        }
        return gels.size();
    }

    @Override
    public void addGel(Class<? extends GelAbility> gel) {
        if (!gels.contains(gel))
            gels.add(gel);
    }

    @Override
    public void addGelToSlot(Class<? extends GelAbility> gel, int slot) {
        if (!gels.contains(gel)) {
            if (slot < 4)
                gels.add(slot, gel);
            else
                gels.add(getUniqueID(), gel);
        }
    }

    @Override
    public int getGelIndex(Class<? extends GelAbility> gel) {
        return gels.indexOf(gel);
    }

    @Override
    public void markEntity(EntityLivingBase entity, int gel) {
        boolean alreadyRegisted = coloredEntities.containsKey(entity.getEntityId());
        if (!alreadyRegisted && GelsPlus.gelEffects) {
            if (!entity.isDead)
                coloredEntities.put(entity.getEntityId(), gel);
        }
    }

    @Override
    public void removeEntity(EntityLivingBase entity) {
        if (coloredEntities.containsKey(entity.getEntityId()))
            coloredEntities.remove(entity.getEntityId());
    }

    @Override
    public Class<? extends GelAbility> getGel(int i) {
        return i < gels.size() ? gels.get(i) : null;
    }

    @Override
    public void addClassToBlackList(Class<? extends Entity> clazz) {
        if (!blackList.contains(clazz))
            blackList.add(clazz);
    }

    @Override
    public void addBlockToBlackList(Block block) {
        if (!blockBlackList.contains(block))
            blockBlackList.add(block);
    }

    @Override
    public List<Class<? extends GelAbility>> getRegistry() {
        return gels;
    }

    @Override
    public HashMap<Integer, Integer> coloredList() {
        return coloredEntities;
    }

    @Override
    public List<Class<? extends Entity>> getBlackList() {
        return blackList;
    }

    @Override
    public List<Block> getBlockBlackList() {
        return blockBlackList;
    }
}
