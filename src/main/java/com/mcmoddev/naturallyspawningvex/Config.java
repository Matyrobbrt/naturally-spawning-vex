package com.mcmoddev.naturallyspawningvex;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

class Config {

	/**
	 *
	 */
	static final ServerConfig SERVER;
	/**
	 *
	 */
	static final ForgeConfigSpec SERVER_SPECIFICATION;
	/**
	 * The default spawn weight of Vex.
	 */
	private static final int VEX_DEFAULT_SPAWN_WEIGHT = 35;

	static {
		Pair<ServerConfig, ForgeConfigSpec> specificationPair =
			new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		SERVER_SPECIFICATION = specificationPair.getRight();
		SERVER = specificationPair.getLeft();
	}

	/**
	 *
	 */
	public static class ServerConfig {
		/**
		 * Boolean value to see if Vex should spawn naturally in all biomes.
		 */
		final ForgeConfigSpec.BooleanValue vexSpawnNaturally;

		/**
		 * Set the spawn weight of Vex.
		 */
		final ForgeConfigSpec.IntValue vexSpawnWeight;

		/**
		 * The biome whitelist. Only biomes listed here will spawn Vex.
		 */
		final ForgeConfigSpec.ConfigValue<List<? extends String>> biomeWhitelist;

		/**
		 * The biome blacklist. Biomes listed here will not have Vex spawn in them.
		 */
		final ForgeConfigSpec.ConfigValue<List<? extends String>> biomeBlacklist;

		/**
		 * @param builder The config builder itself.
		 */
		ServerConfig(final ForgeConfigSpec.Builder builder) {
			vexSpawnNaturally = builder.comment("If Vex should spawn naturally in the world.")
				.define("enableNaturalSpawning", true);

			vexSpawnWeight = builder.comment("If -1, the default spawn weight will be used. (Default = 35)")
				.defineInRange("spawnWeight", VEX_DEFAULT_SPAWN_WEIGHT, -1, Integer.MAX_VALUE);

			biomeWhitelist = builder.comment("If biomes are specified here, Vex will ONLY spawn in these biomes."
				+ " (The blacklist is ignored while this is set!)")
				.defineList("whitelist", Lists.newArrayList(), o -> o instanceof String);

			biomeBlacklist = builder.comment("If the whitelist is not used, use this list to specify the biomes that "
				+ "Vex should not spawn in.")
				.defineList("blacklist", Lists.newArrayList("minecraft:void"), o -> o instanceof String);
		}
	}
}
