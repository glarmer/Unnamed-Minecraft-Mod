package net.lordnoisy.thedarkarcane.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.lordnoisy.thedarkarcane.entity.ModEntities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item TEAR = registerItem("tear", new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item SKINNED_COW_SPAWN_EGG = registerItem("skinned_cow_spawn_egg",
            new SpawnEggItem(ModEntities.SKINNED_COW, 0x948e8d, 0x3b3635,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TheDarkArcane.MOD_ID, name), item);
    }

    public static void registerModItems(){
        TheDarkArcane.LOGGER.info("Registering Mod Items for " + TheDarkArcane.MOD_ID);
    }
}
