package net.lomeli.gels.core;

import java.util.ArrayList;
import java.util.List;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.api.IGelRegistry;

public class GelRegistry implements IGelRegistry {
    private List<GelAbility> gels = new ArrayList<GelAbility>();
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
    public GelAbility getGel(int i) {
        return i < gels.size() ? gels.get(i) : null;
    }

    @Override
    public List<GelAbility> getRegistry() {
        return gels;
    }
}
