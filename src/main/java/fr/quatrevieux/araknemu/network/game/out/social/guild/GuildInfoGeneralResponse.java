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
import fr.quatrevieux.araknemu.game.social.guild.GameGuild;

public class GuildInfoGeneralResponse {
    private final GameGuild guild;

    public GuildInfoGeneralResponse(@Nullable GameGuild guild) {
        this.guild = guild;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("gIG");
        if(guild != null) {
            sb
                .append(guild.members().size() >= 9 ? "1" : "0").append("|")
                .append(guild.level()).append("|")
                .append("10").append("|")
                .append(guild.totalExperience()).append("|")
                .append("100");
        }
        return sb.toString();
    }
}

