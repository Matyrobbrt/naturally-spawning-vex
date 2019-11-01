package com.mcmoddev.naturallyspawningvex;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Config {

	public static final ServerConfig SERVER;
	public static final ForgeConfigSpec SERVER_SPECIFICATION;

	static {
		Pair<ServerConfig, ForgeConfigSpec> specificationPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		SERVER_SPECIFICATION = specificationPair.getRight();
		SERVER = specificationPair.getLeft();
	}

	public static class ServerConfig {
		public final ForgeConfigSpec.BooleanValue VexSpawnNaturally;
		public final ForgeConfigSpec.IntValue VexSpawnWeight;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> BiomeWhitelist;
		public final ForgeConfigSpec.ConfigValue<List<? extends String>> BiomeBlacklist;

		ServerConfig(ForgeConfigSpec.Builder builder) {
			VexSpawnNaturally = builder.comment("If Vex should spawn naturally in the world.")
				.define("enableNaturalSpawning", true);

			VexSpawnWeight = builder.comment("If -1, the default spawn weight will be used. (Default = 35)")
				.defineInRange("spawnWeight", 35, -1, Integer.MAX_VALUE);

			BiomeWhitelist = builder.comment("If biomes are specified here, Vex will ONLY spawn in these biomes. (The blacklist is ignored while this is set!)")
				.defineList("whitelist", Lists.newArrayList(), o -> o instanceof String);

			BiomeBlacklist = builder.comment("If the whitelist is not used, use this list to specify the biomes that Vex should not spawn in.")
				.defineList("blacklist", Lists.newArrayList("minecraft:void"), o -> o instanceof String);
		}
	}
}
