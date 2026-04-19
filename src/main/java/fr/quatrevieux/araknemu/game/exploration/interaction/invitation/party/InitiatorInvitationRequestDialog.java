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

package fr.quatrevieux.araknemu.game.exploration.interaction.invitation.party;

import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.AbstractInitiatorRequestDialog;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.Invitation;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.RequestDialog;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.TargetRequestDialog;

public class InitiatorInvitationRequestDialog extends AbstractInitiatorRequestDialog implements Invitation {
    public InitiatorInvitationRequestDialog(Invitation invitation) {
        super(invitation);
    }

    @Override
    public void cancel(RequestDialog dialog) {

    }

    @Override
    public void accept(TargetRequestDialog dialog) {

    }

    @Override
    public ExplorationPlayer initiator() {
        return null;
    }

    @Override
    public ExplorationPlayer target() {
        return null;
    }

    @Override
    public void send(Object packet) {

    }
}