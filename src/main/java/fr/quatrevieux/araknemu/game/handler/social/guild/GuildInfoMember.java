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

package fr.quatrevieux.araknemu.game.handler.social.guild;

import fr.quatrevieux.araknemu.game.account.GameAccount;
import fr.quatrevieux.araknemu.game.handler.AbstractLoggedPacketHandler;
import fr.quatrevieux.araknemu.game.player.GamePlayer;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.social.guild.GuildInfoMemberRequest;
import fr.quatrevieux.araknemu.network.game.out.social.guild.GuildInfoMemberResponse;

public class GuildInfoMember extends AbstractLoggedPacketHandler<GuildInfoMemberRequest> {

    @Override
    protected void handle(GameSession session, GameAccount account, GuildInfoMemberRequest packet) throws Exception {
        GamePlayer player = session.player();

        if (player == null) {
            return;
        }
        if (player.getGuild() == null) {
            return;
        }
        session.send(new GuildInfoMemberResponse(player.getGuild()));
    }

    @Override
    public Class<GuildInfoMemberRequest> packet() {
        return GuildInfoMemberRequest.class;
    }
}
