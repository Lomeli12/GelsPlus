package net.lomeli.gels.api;

import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

/**
 * Used only to register your Gels. Do NOT implement this.
 *
 * @author Lomeli12
 */
public interface IGelRegistry {
    /**
     * Gets unique gel ID for use with addGelToSlot method. Otherwise, simply
     * use addGel
     *
     * @return unique gel ID
     */
    public int getUniqueID();

    /**
     * Register gel to gel registry
     * <p/>
     * Use: </br><i>addGel(new CustomGelAbility());</i>
     *
     * @param gel
     */
    public void addGel(Class<? extends GelAbility> gel);

    /**
     * Register Gel to specific index, but must greater than 3 (the first 4
     * indexes are reserved for the base gels).
     *
     * @param gel
     * @param index
     */
    public void addGelToSlot(Class<? extends GelAbility> gel, int index);

    /**
     * Get the index of the gel.
     *
     * @param gel
     * @return
     */
    public int getGelIndex(Class<? extends GelAbility> gel);

    /**
     * Apply gel effect to entity.
     *
     * @param entity - Entity to apply effect to
     * @param gel    - Gel ID as it is registered in the GelRegistry
     */
    public void markEntity(EntityLivingBase entity, int gel);

    /**
     * Remove gel effect from entity
     *
     * @param entity
     */
    public void removeEntity(EntityLivingBase entity);

    /**
     * Get gel in corresponding index
     *
     * @param index
     * @return
     */
    public Class<? extends GelAbility> getGel(int index);

    /**
     * Blacklist for gel effects
     *
     * @param clazz
     */
    public void addClassToBlackList(Class<? extends Entity> clazz);

    /**
     * Black list blocks so they can't have gels on themselves
     * @param block
     */
    public void addBlockToBlackList(Block block);

    /**
     * Gets the actual registry.
     *
     * @return
     */
    public List<Class<? extends GelAbility>> getRegistry();

    /**
     * Get list of entities with gel effect applied to them.
     *
     * @return
     */
    public HashMap<Integer, Integer> coloredList();

    /**
     * Get the entity blacklist
     *
     * @return
     */
    public List<Class<? extends Entity>> getBlackList();

    /**
     * Get the block blacklist
     * @return
     */
    public List<Block> getBlockBlackList();
}
