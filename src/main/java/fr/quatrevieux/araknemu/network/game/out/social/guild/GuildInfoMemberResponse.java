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

public class GuildInfoMemberResponse {
    private final GameGuild guild;

    public GuildInfoMemberResponse(@Nullable GameGuild guild) {
        this.guild = guild;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("gIM+");
        boolean first = true;

        if(guild != null) {
            for(GameGuildMember member : guild.members())
            {
                GamePlayer gp = member.getGamePlayer();
                if (first) first = false;
                else sb.append('|');
                sb.append(member.id()).append(";"); // Id
                sb.append(member.getPlayer().name()).append(';'); // Name
                sb.append(member.getPlayer().level()).append(';'); // Level
                sb.append(member.getPlayer().race().ordinal()).append(member.getPlayer().gender().ordinal()).append(';'); // Skin
                sb.append(member.rank()).append(';'); // Rank
                sb.append(member.givenExperience()).append(';'); // Xp given to guild
                sb.append(member.experienceGivenPercent()).append(';'); // %xp given to guild
                sb.append(member.rights()).append(';'); // Rights
                sb.append(gp != null ? "1" : "0").append(';'); // Online
                sb.append("").append(';'); // Alignment
                sb.append("").append(";"); // Last connection
            }
        }
        return sb.toString();
    }
}
