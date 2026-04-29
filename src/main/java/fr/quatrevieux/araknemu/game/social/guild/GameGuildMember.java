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

import fr.quatrevieux.araknemu.data.living.entity.player.Player;
import fr.quatrevieux.araknemu.data.living.entity.social.GuildMember;
import fr.quatrevieux.araknemu.game.player.GamePlayer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class GameGuildMember {
    private final GuildMember entity;
    private final Player player;
    private @Nullable GamePlayer gamePlayer;

    public GameGuildMember(GuildMember entity, Player player) {
        this.entity = entity;
        this.player = player;
    }

    public int id() { return entity.getPlayerId(); }
    public int rank() { return entity.getRank(); }
    public int rights() { return entity.getRights(); }
    public int experienceGivenPercent() { return entity.getGivenXp(); }
    public long givenExperience() { return entity.getGivenXp(); }

    public GuildMember getEntity() {
        return entity;
    }

    public Player getPlayer() { return player; }

    public @Nullable GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void attach(GamePlayer player) {
        this.gamePlayer = player;
    }

    public void detach() {
        this.gamePlayer = null;
    }

    /**
     * Checks if the member has a specific right
     * @param rightValue The right value (power of 2)
     */
    public boolean hasRight(int rightValue) {
        if (rank() == 1) return true;
        return (entity.getRights() & rightValue) == rightValue;
    }
}
