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

package fr.quatrevieux.araknemu.network.game.out.social.guild;

import fr.quatrevieux.araknemu.data.living.entity.social.Guild;
import fr.quatrevieux.araknemu.game.social.guild.GuildService;

public class GuildListResponse {

    private final GuildService guildService;
    private final int playerId;

    public GuildListResponse(GuildService guildService, int playerId) {
        this.guildService = guildService;
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        Guild guild = guildService.getGuild(playerId);
        final StringBuilder sb = new StringBuilder("gIG0|1|0|0|1100");


        return sb.toString();
    }
}
