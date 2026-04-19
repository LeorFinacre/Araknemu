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

package fr.quatrevieux.araknemu.game.exploration.party;

import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import fr.quatrevieux.araknemu.game.exploration.creature.ExplorationCreature;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.Invitation;
import org.checkerframework.checker.initialization.qual.UnknownInitialization;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

import java.util.EnumMap;
import java.util.Map;

/**
 * Aggregate invitation factories for a given creature type
 *
 * @param <C> The supported creature type
 */
public class InvitationFactoryAggregate<C extends ExplorationCreature> implements InvitationFactory<C> {
    private final Map<InvitationType, InvitationTypeFactory<C>> factories = new EnumMap<>(InvitationType.class);

    @SafeVarargs
    public InvitationFactoryAggregate(InvitationTypeFactory<C>... factories) {
        for (InvitationTypeFactory<C> factory : factories) {
            register(factory);
        }
    }

    /**
     * Register a new factory
     */
    @RequiresNonNull("factories")
    protected final void register(@UnknownInitialization InvitationFactoryAggregate<C>this, InvitationTypeFactory<C> factory) {
        factories.put(factory.type(), factory);
    }

    @Override
    public final Invitation create(InvitationType type, ExplorationPlayer initiator, C target) {
        if (!factories.containsKey(type)) {
            throw new IllegalArgumentException("Unsupported type " + type);
        }

        return factories.get(type).create(initiator, target);
    }
}