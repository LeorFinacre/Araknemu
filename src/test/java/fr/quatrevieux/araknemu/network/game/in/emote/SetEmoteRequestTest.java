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
package fr.quatrevieux.araknemu.network.game.in.emote;

import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SetEmoteRequestTest {
    private SetEmoteRequest.Parser parser;

    @BeforeEach
    void setUp() {
        parser = new SetEmoteRequest.Parser();
    }

    @Test
    void invalidEmote() {
        assertThrows(ParsePacketException.class, () -> parser.parse("29"));
    }

    @Test
    void parse() {
        assertEquals(SetEmoteRequest.Emote.SIT.getId(), parser.parse("1").emoteId());
        assertEquals(SetEmoteRequest.Emote.REST.getId(), parser.parse("19").emoteId());
    }
}
