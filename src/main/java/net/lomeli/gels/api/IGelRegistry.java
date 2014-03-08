package net.lomeli.gels.api;

import java.util.List;

/**
 * Used only to register your Gels. Do NOT implement this.
 * @author Lomeli12
 */
public interface IGelRegistry {
    /**
     * Gets unique gel ID for use with addGelToSlot method. Otherwise, simply use addGel
     * @return unique gel ID
     */
    public int getUniqueID();

    /**
     * Register gel to gel registry
     * <p>
     * Use: </br><i>addGel(new CustomGelAbility());</i>
     * @param gel
     */
    public void addGel(GelAbility gel);

    /**
     * Register Gel to specific index, but must greater than 3 (the first 4 indexes are reserved for the base gels).
     * @param gel
     * @param index
     */
    public void addGelToSlot(GelAbility gel, int index);

    /**
     * Get gel in corresponding index
     * @param index
     * @return
     */
    public GelAbility getGel(int index);

    /**
     * Gets the actual registry.
     * @return
     */
    public List<GelAbility> getRegistry();
}
