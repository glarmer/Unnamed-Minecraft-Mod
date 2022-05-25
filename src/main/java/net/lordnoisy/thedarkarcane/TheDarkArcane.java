package net.lordnoisy.thedarkarcane;

import net.fabricmc.api.ModInitializer;
import net.lordnoisy.thedarkarcane.block.ModBlocks;
import net.lordnoisy.thedarkarcane.item.ModItems;
import net.lordnoisy.thedarkarcane.particle.ModParticles;
import net.lordnoisy.thedarkarcane.util.ModRegistries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

public class TheDarkArcane implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "the-dark-arcane";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initialising The Dark Arcane!");


		ModParticles.registerParticles();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModRegistries.registerModStuffs();
		GeckoLib.initialize();
	}
}
