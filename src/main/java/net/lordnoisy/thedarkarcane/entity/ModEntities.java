package net.lordnoisy.thedarkarcane.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.lordnoisy.thedarkarcane.entity.custom.SkinnedCowEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModEntities {
    public static final EntityType<SkinnedCowEntity> SKINNED_COW = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(TheDarkArcane.MOD_ID, "skinned_cow"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SkinnedCowEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f)).build());
}
