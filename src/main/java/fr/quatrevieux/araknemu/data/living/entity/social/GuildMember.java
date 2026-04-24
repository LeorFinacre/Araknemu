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

public class GuildMember {
    private final int player_id;
    private final int guild_id;
    private final int rank;
    private final int given_xp;
    private final int rights;
    private final int align;

    public GuildMember(int playerId, int guildId, int rank, int givenXp, int rights, int align) {
        player_id = playerId;
        guild_id = guildId;
        this.rank = rank;
        given_xp = givenXp;
        this.rights = rights;
        this.align = align;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public int getGuild_id() {
        return guild_id;
    }

    public int getRank() {
        return rank;
    }

    public int getGiven_xp() {
        return given_xp;
    }

    public int getRights() {
        return rights;
    }

    public int getAlign() {
        return align;
    }
}
