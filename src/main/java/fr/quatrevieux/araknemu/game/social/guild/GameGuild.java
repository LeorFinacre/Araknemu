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

import fr.quatrevieux.araknemu.data.living.entity.social.PlayerGuild;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameGuild {
    private final PlayerGuild entity;
    private final List<GameGuildMember> members;
    // private final List<TaxCollector> taxCollectors;

    public GameGuild(PlayerGuild entity, List<GameGuildMember> members) {
        this.entity = entity;
        this.members = new CopyOnWriteArrayList<>(members);
    }

    public int id() { return entity.getId(); }
    public String name() { return entity.getName(); }
    public int level() { return entity.getLevel(); }
    public String emblem() { return entity.getEmblem().encode(); }

    /**
     * Retrieves a specific member by their character ID
     */
    public @Nullable GameGuildMember member(int characterId) {
        for (GameGuildMember member : members) {
            if (member.id() == characterId) {
                return member;
            }
        }
        return null;
    }

    /**
     * Add a new member to the guild
     */
    public void addMember(GameGuildMember member) {
        this.members.add(member);
    }

    /**
     * Removes a member (ban or departure)
     */
    public void removeMember(int characterId) {
        members.removeIf(m -> m.id() == characterId);
    }

    /**
     * Calculating the total experience
     */
    public long totalExperience() {
        return entity.getExperience();
    }

    public List<GameGuildMember> members() {
        return Collections.unmodifiableList(members);
    }
}