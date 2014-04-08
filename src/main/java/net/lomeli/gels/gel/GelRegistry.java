package net.lomeli.gels.gel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.api.IGelRegistry;

public class GelRegistry implements IGelRegistry {
    private List<GelAbility> gels = new ArrayList<GelAbility>();
    private List<Class<?>> blackList = new ArrayList<Class<?>>();
    private HashMap<Integer, Integer> coloredEntities = new HashMap<Integer, Integer>();

    public void initRegistry() {
        addGel(new GelPropulsion());
        addGel(new GelRepulsion());
        addGel(new GelAdhesion());
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
    public void addGel(GelAbility gel) {
        if (!gels.contains(gel))
            gels.add(gel);
    }

    @Override
    public void addGelToSlot(GelAbility gel, int slot) {
        if (!gels.contains(gel)) {
            if (slot < 4)
                gels.add(slot, gel);
            else
                gels.add(getUniqueID(), gel);
        }
    }

    @Override
    public int getGelIndex(GelAbility gel) {
        return gels.indexOf(gel);
    }

    @Override
    public void markEntity(EntityLivingBase entity, int gel) {
        if ((!coloredEntities.containsKey(entity.getEntityId())) && GelsPlus.gelEffects) {
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
    public GelAbility getGel(int i) {
        return i < gels.size() ? gels.get(i) : null;
    }

    @Override
    public void addClassToBlackList(Class<?> clazz) {
        if (!blackList.contains(clazz))
            blackList.add(clazz);
    }

    @Override
    public List<GelAbility> getRegistry() {
        return gels;
    }

    @Override
    public HashMap<Integer, Integer> coloredList() {
        return coloredEntities;
    }

    @Override
    public List<Class<?>> getBlackList() {
        return blackList;
    }
}
