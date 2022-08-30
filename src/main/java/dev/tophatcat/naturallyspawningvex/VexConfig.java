/*
 * Naturally Spawning Vex - https://github.com/tophatcats-mods/naturally-spawning-vex
 * Copyright (C) 2016-2022 <KiriCattus>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * Specifically version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package dev.tophatcat.naturallyspawningvex;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

class VexConfig {

    static final ServerConfig CONFIG;
    static final ForgeConfigSpec SERVER_SPEC;

    static {
        Pair<ServerConfig, ForgeConfigSpec> specificationPair =
            new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specificationPair.getRight();
        CONFIG = specificationPair.getLeft();
    }


    static class ServerConfig {
        final ForgeConfigSpec.IntValue vexSpawnWeight;
        final ForgeConfigSpec.IntValue vexMinGroupCount;
        final ForgeConfigSpec.IntValue vexMaxGroupCount;

        ServerConfig(final ForgeConfigSpec.Builder builder) {
            builder.push("Vex Spawn Configurations");
            builder.comment("Set how much of a chance Vex have to spawn in the Overworld, along with the min/max group "
                + "count. If the spawn weight is set to 0 We will not attempt to cause Vex to spawn. "
                + "However Vanilla caused spawns will still happen!");

            vexSpawnWeight = builder.defineInRange("Vex Spawn Weight", 25, 0, 200);

            vexMinGroupCount = builder.defineInRange("Minimum Group Count", 2, 1, 64);

            vexMaxGroupCount = builder.defineInRange("Maximum Group Count", 5, 1, 64);
        }
    }
}
