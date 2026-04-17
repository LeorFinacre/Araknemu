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

import fr.quatrevieux.araknemu.game.GameBaseCase;
import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import fr.quatrevieux.araknemu.game.player.Restrictions;
import fr.quatrevieux.araknemu.network.game.in.emote.SetEmoteRequest;
import fr.quatrevieux.araknemu.network.game.out.basic.Noop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayEmoteTest extends GameBaseCase {
    private PlayEmote handler;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        handler = new PlayEmote();
    }

    @Test
    void handleSuccess() throws Exception {
        ExplorationPlayer exploration = explorationPlayer();

        handler.handle(session, new SetEmoteRequest(19, true));
    }

    @Test
    void handleWithRestrictedDirection() throws Exception {
        ExplorationPlayer exploration = explorationPlayer();
        exploration.player().restrictions().unset(Restrictions.Restriction.ALLOW_MOVE_ALL_DIRECTION);

        assertErrorPacket(new Noop(), () -> handler.handle(session, new SetEmoteRequest(19, true)));
    }
}
