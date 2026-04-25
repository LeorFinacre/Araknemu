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

import fr.quatrevieux.araknemu.core.network.parser.Packet;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.core.network.parser.SinglePacketParser;
import fr.quatrevieux.araknemu.util.ParseUtils;
import org.checkerframework.common.value.qual.MinLen;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SetEmoteRequest implements Packet {
    public enum Emote {
        SIT(1, 1),
        BYE(2, 2),
        APPL(3, 4),
        MAD(4, 8),
        FEAR(5, 16),
        WIP(6, 32),
        PIPO(7, 64),
        OUPS(8, 128),
        HI(9, 256),
        KISS(10, 512),
        PFC1(11, 1024),
        PFC2(12, 2048),
        PFC3(13, 4096),
        CROSS(14, 8192),
        POINT(15, 16384),
        CROW(16, 32768),
        REST(19, 262144),
        CHAMP(21, 1048576),
        AURA(22, 2097152),
        BAT(23, 4194304);

        private final int id;
        private final long bitmask;

        Emote(int id, long bitmask) {
            this.id = id;
            this.bitmask = bitmask;
        }

        public int getId() { return id; }
        public long getBitmask() { return bitmask; }
    }

    private final int emoteId;

    public SetEmoteRequest(int emoteId) {
        this.emoteId = emoteId;
    }

    public int emoteId() {
        return emoteId;
    }

    public static final class Parser implements SinglePacketParser<SetEmoteRequest> {
        private static final Map<Integer, Emote> emotes = Arrays.stream(Emote.values())
                .collect(Collectors.toMap(Emote::getId, Function.identity()));

        @Override
        public SetEmoteRequest parse(String input) throws ParsePacketException {
            final int emoteId = ParseUtils.parsePositiveInt(input);
            Emote emote = emotes.get(emoteId);
            if (emote == null) {
                throw new ParsePacketException(code() + input, "Emote ID " + emoteId + " is not supported");
            }
            return new SetEmoteRequest(emote.getId());
        }

        @Override
        public @MinLen(2) String code() {
            return "eU";
        }
    }
}
