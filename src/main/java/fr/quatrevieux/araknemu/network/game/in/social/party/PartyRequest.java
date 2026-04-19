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

package fr.quatrevieux.araknemu.network.game.in.social.party;

import fr.quatrevieux.araknemu.core.network.parser.Packet;
import fr.quatrevieux.araknemu.core.network.parser.PacketTokenizer;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.core.network.parser.SinglePacketParser;
import fr.quatrevieux.araknemu.game.exploration.party.InvitationType;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.value.qual.MinLen;

import java.util.Optional;

public class PartyRequest implements Packet {
    private final InvitationType type;
    private final @Nullable Integer id;
    private final @Nullable Integer cell;

    public PartyRequest(InvitationType type, @Nullable Integer id, @Nullable Integer cell) {
        this.type = type;
        this.id = id;
        this.cell = cell;
    }

    /**
     * Get the invitation type
     */
    public InvitationType type() {
        return type;
    }

    /**
     * Get the target id
     * May be not provided
     */
    public Optional<Integer> id() {
        return Optional.ofNullable(id);
    }

    /**
     * Get the target cell
     * May be not provided
     */
    public Optional<Integer> cell() {
        return Optional.ofNullable(cell);
    }

    public static final class Parser implements SinglePacketParser<PartyRequest> {
        private final InvitationType[] types = InvitationType.values();

        @Override
        public PartyRequest parse(String input) throws ParsePacketException {
            final PacketTokenizer tokenizer = tokenize(input, '|');

            final int typeId = tokenizer.nextInt();
            final String targetId = tokenizer.nextPart();
            final String cellId = tokenizer.nextPartOrDefault("");

            if (typeId < 0 || typeId >= types.length) {
                throw new ParsePacketException(code() + input, "Invalid invitation type");
            }

            return new PartyRequest(
                    types[typeId],
                    !targetId.isEmpty() ? Integer.parseInt(targetId) : null,
                    !cellId.isEmpty() ? Integer.parseInt(cellId) : null
            );
        }

        @Override
        public @MinLen(2) String code() {
            return "PI";
        }
    }
}
