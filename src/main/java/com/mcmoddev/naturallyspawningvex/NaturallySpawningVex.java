package com.mcmoddev.naturallyspawningvex;

import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.VexEntity;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mod(value = NaturallySpawningVex.MODID)
@Mod.EventBusSubscriber(modid = NaturallySpawningVex.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NaturallySpawningVex {

	/**
	 * Set the mods ID.
	 */
	public static final String MODID = "naturallyspawningvex";

	/**
	 * Setup the logger for the mod.
	 */
	private static final Logger LOGGER = LogManager.getLogger("Naturally Spawning Vex");

	/**
	 * Register things.
	 */
	public NaturallySpawningVex() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::fingerprintViolation);
		ModLoadingContext modLoadingContext = ModLoadingContext.get();
		modLoadingContext.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPECIFICATION);
	}

	/**
	 * Check if the mod is signed and warn if it is not.
	 *
	 * @param event the event fired.
	 */
	private void fingerprintViolation(FMLFingerprintViolationEvent event) {
		LOGGER.error("Invalid fingerprint detected! The file " + event.getSource().getName() + " may have been " +
			"tampered with. This version will NOT be supported! Please download the mod from CurseForge for a " +
			"supported and signed version of the mod.");
	}

	private static Collection<Biome> biomes = null;
	private static Biome.SpawnListEntry entry = null;

	@SubscribeEvent
	public static void onLoad(ModConfig.Loading event) {
		if (event.getConfig().getSpec() != Config.SERVER_SPECIFICATION) {
			return;
		}

		if (entry != null) {
			biomes.stream().map(biome -> biome.getSpawns(EntityClassification.MONSTER)).forEach(list -> list.remove(entry));
			biomes = Collections.emptyList();
		}

		if (Config.SERVER.VexSpawnNaturally.get()) {
			int currentWeight = Config.SERVER.VexSpawnWeight.get();

			if (currentWeight > 0) {
				biomes = ForgeRegistries.BIOMES.getValues();
				if (Config.SERVER.BiomeWhitelist.get() != null && Config.SERVER.BiomeWhitelist.get().size() > 0) {
					Set<String> whitelist = Sets.newHashSet(Config.SERVER.BiomeWhitelist.get());
					biomes = biomes.stream().filter(b -> whitelist.contains(b.getRegistryName().toString())).collect(Collectors.toList());
				} else {
					if (Config.SERVER.BiomeBlacklist.get() != null && Config.SERVER.BiomeBlacklist.get().size() > 0) {
						Set<String> blacklist = Sets.newHashSet(Config.SERVER.BiomeBlacklist.get());
						biomes = biomes.stream().filter(b -> !blacklist.contains(b.getRegistryName().toString())).collect(Collectors.toList());
					}
				}

				entry = new Biome.SpawnListEntry(EntityType.VEX, Config.SERVER.VexSpawnWeight.get(), 1, 5);
				biomes.stream().map(biome -> biome.getSpawns(EntityClassification.MONSTER)).forEach(list -> list.add(entry));
			}
		}
	}
}
