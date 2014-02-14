package net.lomeli.gels.entity;

import net.lomeli.gels.GelsPlus;

import cpw.mods.fml.common.registry.EntityRegistry;

public class ModEntities {
    public static void registerEntities() {
        EntityRegistry.registerModEntity(EntityAdhesiveGel.class, "entity.GelsPlus:adGel",
                EntityRegistry.findGlobalUniqueEntityId(), GelsPlus.instance, 64, 1, false);
    }
}
