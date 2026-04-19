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

/**
 * Simply define the factory with constructor's parameters
 *
 * @param <C> The creature type target
 */
public class SimpleInvitationTypeFactory<C extends ExplorationCreature> implements InvitationTypeFactory<C> {
    private final InvitationType type;
    private final Factory<C> factory;

    public SimpleInvitationTypeFactory(InvitationType type, Factory<C> factory) {
        this.type = type;
        this.factory = factory;
    }

    @Override
    public InvitationType type() {
        return type;
    }

    @Override
    public Invitation create(ExplorationPlayer initiator, C target) {
        return factory.create(initiator, target);
    }

    @FunctionalInterface
    public interface Factory<C extends ExplorationCreature> {
        public Invitation create(ExplorationPlayer initiator, C target);
    }
}