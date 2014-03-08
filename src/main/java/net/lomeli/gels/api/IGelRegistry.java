package net.lomeli.gels.api;

import java.util.List;

public interface IGelRegistry {
    public int getUniqueID();

    public void addGel(GelAbility gel);

    public void addGelToSlot(GelAbility gel, int slot);

    public GelAbility getGel(int i);

    public List<GelAbility> getRegistry();
}
