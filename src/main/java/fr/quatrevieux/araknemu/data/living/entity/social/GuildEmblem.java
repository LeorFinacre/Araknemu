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

package fr.quatrevieux.araknemu.data.living.entity.social;

import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class GuildEmblem {
    private final @NonNegative int backgroundShape;
    private final @NonNegative int backgroundColor;
    private final @NonNegative int symbolShape;
    private final @NonNegative int symbolColor;

    public GuildEmblem(@NonNegative int backgroundShape, @NonNegative int backgroundColor, @NonNegative int symbolShape, @NonNegative int symbolColor) {
        this.backgroundShape = backgroundShape;
        this.backgroundColor = backgroundColor;
        this.symbolShape = symbolShape;
        this.symbolColor = symbolColor;
    }

    /**
     * Parses a string from the database (ex: "7,16777215,1,0")
     */
    public static @Nullable GuildEmblem parse(String data) {
        String[] parts = data.split(",");

        if (parts.length != 4) {
            return null;
        }

        try {
            int bgShape = Integer.parseInt(parts[0]);
            int bgColor = Integer.parseInt(parts[1]);
            int syShape = Integer.parseInt(parts[2]);
            int syColor = Integer.parseInt(parts[3]);
            return new GuildEmblem(
                    (@NonNegative int) bgShape,
                    (@NonNegative int) bgColor,
                    (@NonNegative int) syShape,
                    (@NonNegative int) syColor
            );
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Format the emblem for database storage
     */
    @Override
    public String toString() {
        return backgroundShape + "," + backgroundColor + "," + symbolShape + "," + symbolColor;
    }

    /**
     * Encode the emblem for the gS package (Base 36)
     * Format expected by the client : backgroundShape,backgroundColor,symbolShape,symbolColor
     */
    public String encode() {
        return Integer.toString(backgroundShape, 36) + "|" +
                Integer.toString(backgroundColor, 36) + "|" +
                Integer.toString(symbolShape, 36) + "|" +
                Integer.toString(symbolColor, 36);
    }

    public int getBackgroundShape() {
        return backgroundShape;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getSymbolShape() {
        return symbolShape;
    }

    public int getSymbolColor() {
        return symbolColor;
    }
}