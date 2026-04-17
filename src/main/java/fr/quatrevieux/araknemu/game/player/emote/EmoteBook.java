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

package fr.quatrevieux.araknemu.game.player.emote;

import fr.quatrevieux.araknemu.core.event.Dispatcher;
import fr.quatrevieux.araknemu.data.living.entity.player.Player;
import fr.quatrevieux.araknemu.game.player.emote.event.EmoteLearned;
import fr.quatrevieux.araknemu.network.game.in.emote.SetEmoteRequest;
import org.checkerframework.checker.nullness.qual.EnsuresKeyForIf;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EmoteBook implements EmoteList, Dispatcher {
    private final Dispatcher dispatcher;
    private final Player player;
    Map<Integer, SetEmoteRequest.Emote> entries = new HashMap<>();

    @SuppressWarnings("argument")
    public EmoteBook(Dispatcher dispatcher, Player player) {
        this.dispatcher = dispatcher;
        this.player = player;
        for (SetEmoteRequest.Emote emote : SetEmoteRequest.Emote.values()) {
            if (emote != SetEmoteRequest.Emote.NONE && (player.emotes() & emote.getBitmask()) != 0) {
                this.entries.put(emote.getId(), emote);
            }
        }
    }

    @Override
    public SetEmoteRequest.Emote get(int emoteId) {
        SetEmoteRequest.Emote emote = entries.get(emoteId);

        return emote != null ? emote : SetEmoteRequest.Emote.NONE;
    }

    @Override
    public void dispatch(Object event) {
        dispatcher.dispatch(event);
    }

    @Override
    public Iterator<SetEmoteRequest.Emote> iterator() {
        return entries.values().stream()
                .iterator()
                ;
    }

    /**
     * Get all available emotes
     */
    public Collection<SetEmoteRequest.Emote> all() {
        return entries.values();
    }


    /**
     * Check if the user has the emote
     *
     * @param emoteId Emote to check
     */
    @Override
    @EnsuresKeyForIf(result = true, expression = "#1", map = "entries")
    @SuppressWarnings("contracts.conditional.postcondition") // checker do not consider null check as key existence
    public boolean has(int emoteId) {
        final SetEmoteRequest.Emote entry = entries.get(emoteId);

        return entry != null;
    }

    /**
     * Check if the player can learn the emote
     *
     * @param emote Emote to learn
     */
    public boolean canLearn(SetEmoteRequest.Emote emote) {
        return !has(emote.getId());
    }

    /**
     * Learn an emote
     */
    public void learn(SetEmoteRequest.Emote emoteToLearn) {
        if (!canLearn(emoteToLearn)) {
            throw new IllegalArgumentException("Cannot learn the emote " + emoteToLearn.name() + " (" + emoteToLearn.getId() + ")");
        }
        player.setEmotes(Math.max(player.emotes() | emoteToLearn.getBitmask(), 0));
        entries.put(emoteToLearn.getId(), emoteToLearn);
        dispatch(new EmoteLearned(emoteToLearn));
    }

    /**
     * Return the raw data of emotes known for a player
     */
    public long raw() {
        return player.emotes();
        }
}
