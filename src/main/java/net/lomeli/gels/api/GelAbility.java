package net.lomeli.gels.api;

import java.awt.Color;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

/**
 * Extend this in order to make your gel.
 *
 * @author Lomeli12
 */
public abstract class GelAbility {
    /**
     * Use this to register your gels. </p> Example:</br> gelRegistry.addGel(new
     * GelCustom());
     */
    public static IGelRegistry gelRegistry;

    /**
     * The effect the gel has on entities that come into contact with it.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @param side
     * @param entity
     * @param doEffect - If the entity is a player, will be false if they're sneaking
     */
    public abstract void gelEffect(World world, int x, int y, int z, int side, Entity entity, boolean doEffect);

    /**
     * The effect of the gel blob when it hits an entity.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @param entity
     * @param doEffect - If the entity is a player, will be false if they're sneaking
     */
    public abstract void gelThrownEffect(World world, int x, int y, int z, Entity entity, boolean doEffect);

    /**
     * The effect the gel will have on a mob if it gets colored.
     *
     * @param world
     * @param entity
     * @param doEffect - If the entity is a player, will be false if they're sneaking
     */
    public abstract void markedEntityEffect(World world, EntityLivingBase entity, boolean doEffect);

    /**
     * The Color of the gel. If left null, color will be set to white.
     *
     * @return
     */
    public abstract Color gelColor();

    /**
     * Items used in the gel's shapeless recipe (8 items max). If you want a
     * fluid container in the recipe, use <i>fluid$</i> plus the fluid's name as
     * it is registered in the Fluid Registry.
     * <p/>
     * Examples: <br>
     * For Water containers (Water Bucket, Water Bottles, etc) <br>
     * <i>return new Object[] { "fluid$water" };</i> <br>
     * For Lava containers (Lava Bucket, Lava Capsules, Lava Cells, etc) <br>
     * <i>return new Object[] { "fluid$lava" };</i> <br>
     * Basic recipe <br>
     * <i>return new Object[] { Items.slime_ball, "fluid$water" };</i>
     *
     * @return
     */
    public abstract Object[] recipeItems();

    /**
     * Get the name of the gel here. If doing localizations, place the
     * unlocalized name of the gel here.
     *
     * @return
     */
    public abstract String gelName();

    /**
     * If you want the blob form of the gel to be throwable.
     *
     * @return
     */
    public abstract boolean isThrowable();

    /**
     * Can it color a mob
     *
     * @return
     */
    public abstract boolean canColor();
}
