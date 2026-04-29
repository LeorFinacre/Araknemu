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

import com.github.javaparser.quality.Nullable;
import fr.quatrevieux.araknemu.game.player.GamePlayer;
import fr.quatrevieux.araknemu.game.social.guild.GameGuild;
import fr.quatrevieux.araknemu.game.social.guild.GameGuildMember;

public class GuildStatsResponse {
    private final GamePlayer player;

    public GuildStatsResponse(@Nullable GamePlayer player) {
        this.player = player;
    }

    @Override
    public String toString() {
        GameGuild guild = player.getGuild();
        final StringBuilder sb = new StringBuilder("gS");
        if(guild != null) {
            GameGuildMember guildMember = guild.member(player.id());
            if(guildMember != null) {
                sb
                    .append(guild.name()).append("|")
                    .append(guild.emblem()).append("|")
                    .append(guildMember.rights());
            }
        }
        return sb.toString();
    }
}
