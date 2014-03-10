package net.lomeli.gels.gel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;

import net.lomeli.lomlib.network.PacketHandler;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.api.IGelRegistry;
import net.lomeli.gels.network.PacketNBT;

public class GelRegistry implements IGelRegistry {
    private List<GelAbility> gels = new ArrayList<GelAbility>();
    private HashMap<Integer, Integer> coloredEntities = new HashMap<Integer, Integer>();

    private static GelRegistry instance;

    public static GelRegistry getInstance() {
        if (instance == null)
            instance = new GelRegistry();
        return instance;
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
        if (!coloredEntities.containsKey(entity.getEntityId())) {
            if (!entity.isDead) {
                coloredEntities.put(entity.getEntityId(), gel);
                entity.getEntityData().setInteger("gelEffect", gel);
                PacketHandler.sendToServer(GelsPlus.packetChannel.getChannel(), new PacketNBT(entity, gel, true));
            }
        }
    }

    @Override
    public void removeEntity(EntityLivingBase entity) {
        coloredEntities.remove(entity.getEntityId());
        entity.getEntityData().removeTag("gelEffect");
        PacketHandler.sendToServer(GelsPlus.packetChannel.getChannel(), new PacketNBT(entity, 0, false));
    }

    @Override
    public GelAbility getGel(int i) {
        return i < gels.size() ? gels.get(i) : null;
    }

    @Override
    public List<GelAbility> getRegistry() {
        return gels;
    }

    @Override
    public HashMap<Integer, Integer> coloredList() {
        return coloredEntities;
    }
}
