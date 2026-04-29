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

package fr.quatrevieux.araknemu.data.world.transformer;

import fr.quatrevieux.araknemu.data.living.entity.social.GuildEmblem;
import fr.quatrevieux.araknemu.data.transformer.Transformer;
import fr.quatrevieux.araknemu.data.transformer.TransformerException;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.PolyNull;

public final class GuildEmblemTransformer implements Transformer<GuildEmblem> {
    @Override
    public @PolyNull String serialize(@PolyNull GuildEmblem value) {
        if (value == null) {
            return null;
        }
        return value.getBackgroundShape() + "," +
                value.getBackgroundColor() + "," +
                value.getSymbolShape() + "," +
                value.getSymbolColor();
    }

    @Override
    public @NonNull GuildEmblem unserialize(@PolyNull String serialize) throws TransformerException {
        if (serialize == null || serialize.isEmpty()) {
            throw new IllegalArgumentException("Guild emblem cannot be empty");
        }

        String[] parts = serialize.split(",");

        if (parts.length != 4) {
            throw new TransformerException("Invalid guild emblem data: " + serialize);
        }

        try {
            int bgShape = Integer.parseInt(parts[0]);
            int bgColor = Integer.parseInt(parts[1]);
            int syShape = Integer.parseInt(parts[2]);
            int syColor = Integer.parseInt(parts[3]);

            if (bgShape < 0 || bgColor < 0 || syShape < 0 || syColor < 0) {
                throw new TransformerException("Negative emblem values are not allowed");
            }
            return new GuildEmblem(
                    (@NonNegative int) bgShape,
                    (@NonNegative int) bgColor,
                    (@NonNegative int) syShape,
                    (@NonNegative int) syColor
            );
        } catch (NumberFormatException e) {
            throw new TransformerException("Error while parsing emblem integers", e);
        }
    }
}
