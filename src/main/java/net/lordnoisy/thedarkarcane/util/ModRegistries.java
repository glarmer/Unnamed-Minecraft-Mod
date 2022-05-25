package net.lordnoisy.thedarkarcane.util;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.lordnoisy.thedarkarcane.entity.ModEntities;
import net.lordnoisy.thedarkarcane.entity.custom.SkinnedCowEntity;

public class ModRegistries {
    public static void registerModStuffs(){
        TheDarkArcane.LOGGER.info("TheDA Register Mod Stuffs is called " + TheDarkArcane.MOD_ID);
        registerAttributes();
    }

    private static void registerAttributes() {
        TheDarkArcane.LOGGER.info("TheDA Registering raccoon attributes " + TheDarkArcane.MOD_ID);
        FabricDefaultAttributeRegistry.register(ModEntities.SKINNED_COW, SkinnedCowEntity.setAttributes());
    }
}
