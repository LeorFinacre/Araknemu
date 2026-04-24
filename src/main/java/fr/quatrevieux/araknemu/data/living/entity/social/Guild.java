/*
 * This file is part of Araknemu.
 *
 * Araknemu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Araknemu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Araknemu.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (c) 2017-2026 Leor Finacre
 */

package fr.quatrevieux.araknemu.data.living.entity.social;

public class Guild {
    private final int id;
    private final String name;
    private final String emblem;
    private final int level;
    private final int experience;
    private final int capital;
    private final int perco_max;
    private final String spells;
    private final String stats;

    public Guild(int id, String name, String emblem, int level, int experience, int capital, int percoMax, String spells, String stats) {
        this.id = id;
        this.name = name;
        this.emblem = emblem;
        this.level = level;
        this.experience = experience;
        this.capital = capital;
        perco_max = percoMax;
        this.spells = spells;
        this.stats = stats;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmblem() {
        return emblem;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getCapital() {
        return capital;
    }

    public int getPerco_max() {
        return perco_max;
    }

    public String getSpells() {
        return spells;
    }

    public String getStats() {
        return stats;
    }
}
