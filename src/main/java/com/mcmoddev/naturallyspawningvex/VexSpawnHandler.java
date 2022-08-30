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
package com.mcmoddev.naturallyspawningvex;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;

class VexSpawnHandler {

    static void onLoad(BiomeLoadingEvent event) {
        if (event.getName() != null) {
            if (event.getCategory() != Biome.Category.NETHER
                && event.getCategory() != Biome.Category.THEEND) {
                event.getSpawns().getSpawner(EntityClassification.MONSTER)
                    .add(new MobSpawnInfo.Spawners(EntityType.VEX,
                    VexConfig.CONFIG.vexSpawnWeight.get(),
                    VexConfig.CONFIG.vexMinGroupCount.get(),
                    VexConfig.CONFIG.vexMaxGroupCount.get()));
            }
        }
    }
}
