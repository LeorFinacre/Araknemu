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
import fr.quatrevieux.araknemu.game.exploration.creature.Operation;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.Invitation;
import org.checkerframework.checker.nullness.qual.Nullable;

public class DefaultInvitationFactory implements InvitationFactory<ExplorationCreature> {
    private final InvitationFactory<ExplorationPlayer> playerFactory;

    public DefaultInvitationFactory(InvitationFactory<ExplorationPlayer> playerFactory) {
        this.playerFactory = playerFactory;
    }

    @Override
    public Invitation create(InvitationType type, ExplorationPlayer initiator, ExplorationCreature target) {
        final @Nullable Invitation invitation = target.apply(new DefaultInvitationFactory.CreateInvitation(type, initiator));

        if (invitation == null) {
            throw new IllegalArgumentException("Bad target");
        }

        return invitation;
    }

    /**
     * Visitor operation for create the invitation on the valid target
     */
    private final class CreateInvitation implements Operation<Invitation> {
        private final InvitationType type;
        private final ExplorationPlayer initiator;

        public CreateInvitation(InvitationType type, ExplorationPlayer initiator) {
            this.type = type;
            this.initiator = initiator;
        }

        @Override
        public Invitation onExplorationPlayer(ExplorationPlayer player) {
            return playerFactory.create(type, initiator, player);
        }
    }
}
