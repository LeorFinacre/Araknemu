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
package fr.quatrevieux.araknemu.game.handler.emote;

import fr.quatrevieux.araknemu.core.network.exception.ErrorPacket;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import fr.quatrevieux.araknemu.game.handler.AbstractExploringPacketHandler;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.emote.SetEmoteRequest;
import fr.quatrevieux.araknemu.network.game.out.basic.Noop;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayEmote extends AbstractExploringPacketHandler<SetEmoteRequest> {
    private static final Map<Integer, SetEmoteRequest.Emote> emotes = Arrays.stream(SetEmoteRequest.Emote.values())
            .collect(Collectors.toMap(SetEmoteRequest.Emote::getId, Function.identity()));
    @Override
    protected void handle(GameSession session, ExplorationPlayer exploration, SetEmoteRequest packet) throws Exception {
        if (!exploration.player().restrictions().canMoveAllDirections()) {
            throw new ErrorPacket(new Noop());
        }
        SetEmoteRequest.Emote emote = emotes.get(packet.emoteId());
        if (emote == null) {
            throw new ParsePacketException(String.valueOf(packet.emoteId()), "Emote ID " + packet.emoteId() + " is not supported");
        }
        exploration.setCurrentEmote(emote);
    }

    @Override
    public Class<SetEmoteRequest> packet() {
        return SetEmoteRequest.class;
    }
}
