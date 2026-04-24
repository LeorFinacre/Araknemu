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

package fr.quatrevieux.araknemu.game.social.guild;

import fr.quatrevieux.araknemu.core.event.EventsSubscriber;
import fr.quatrevieux.araknemu.core.event.Listener;
import fr.quatrevieux.araknemu.data.living.entity.social.Guild;
import fr.quatrevieux.araknemu.data.living.entity.social.GuildMember;
import fr.quatrevieux.araknemu.data.living.repository.player.PlayerRepository;
import fr.quatrevieux.araknemu.data.living.repository.social.guild.GuildMemberRepository;
import fr.quatrevieux.araknemu.data.living.repository.social.guild.GuildRepository;

public class GuildService implements EventsSubscriber {
    private final GuildRepository guildRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final PlayerRepository playerRepository;

    public GuildService(GuildRepository guildRepository, GuildMemberRepository guildMemberRepository, PlayerRepository playerRepository) {
        this.guildRepository = guildRepository;
        this.guildMemberRepository = guildMemberRepository;
        this.playerRepository = playerRepository;
    }

    public Guild getGuild(int playerId) {
        GuildMember guildMember = guildMemberRepository.getGuildIdByPlayerId(playerId);
        return guildRepository.getGuildById(guildMember.getPlayer_id());
    }

    @Override
    public Listener[] listeners() {
        return new Listener[0];
    }
}
