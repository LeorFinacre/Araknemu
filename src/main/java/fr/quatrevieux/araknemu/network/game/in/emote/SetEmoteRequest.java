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
        NONE(0, 0, false),
        SIT(1, 1, true),
        BYE(2, 2, false),
        APPL(3, 4, false),
        MAD(4, 8, false),
        FEAR(5, 16, false),
        WIP(6, 32, false),
        PIPO(7, 64, false),
        OUPS(8, 128, false),
        HI(9, 256, false),
        KISS(10, 512, false),
        PFC1(11, 1024, false),
        PFC2(12, 2048, false),
        PFC3(13, 4096, false),
        CROSS(14, 8192, true),
        POINT(15, 16384, true),
        CROW(16, 32768, false),
        REST(19, 262144, true),
        CHAMP(21, 1048576, false),
        AURA(22, 2097152, false),
        BAT(23, 4194304, false);

        private final int id;
        private final long bitmask;
        private final boolean isStatic;

        Emote(int id, long bitmask, boolean isStatic) {
            this.id = id;
            this.bitmask = bitmask;
            this.isStatic = isStatic;
        }

        public int getId() { return id; }
        public long getBitmask() { return bitmask; }
        public boolean isStatic() { return isStatic; }

        public static SetEmoteRequest.Emote fromId(int id) {
            for (SetEmoteRequest.Emote e : values()) {
                if (e.getId() == id) return e;
            }
            return SetEmoteRequest.Emote.NONE;
        }
    }

    private final int emoteId;

    public SetEmoteRequest(int emoteId, boolean isStatic) {
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
            return new SetEmoteRequest(emote.getId(), emote.isStatic());
        }

        @Override
        public @MinLen(2) String code() {
            return "eU";
        }
    }
}
