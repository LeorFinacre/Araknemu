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
import fr.quatrevieux.araknemu.game.exploration.interaction.Interaction;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.Invitation;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.InvitationHandler;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.RequestDialog;
import fr.quatrevieux.araknemu.game.exploration.interaction.request.TargetRequestDialog;
import fr.quatrevieux.araknemu.game.exploration.map.ExplorationMap;
import fr.quatrevieux.araknemu.network.game.out.social.party.InvitationRequested;
import fr.quatrevieux.araknemu.network.game.out.social.party.PartyInvitationRequestError;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PlayerInvitationRequest implements Invitation, InvitationHandler {
    private final Invitation invitation;

    @SuppressWarnings("method.invocation")
    public PlayerInvitationRequest(ExplorationPlayer initiator, ExplorationPlayer target) {
        invitation = invitation(initiator, target);
    }

    @Override
    public void accept(Invitation invitation, TargetRequestDialog dialog) {

    }

    @Override
    public void acknowledge(Invitation invitation) {
        invitation.send(new InvitationRequested(invitation.initiator(), invitation.target()));
    }

    @Override
    public void refuse(Invitation invitation, RequestDialog dialog) {

    }

    @Override
    public RequestDialog initiatorDialog(Invitation invitation) {
        return new InitiatorInvitationRequestDialog(invitation);
    }

    @Override
    public TargetRequestDialog targetDialog(Invitation invitation) {
        return null;
    }

    @Override
    public boolean check(Invitation invitation) {
        if (invitation.target().interactions().busy()) {
            if (invitation.target().interactions().get(Interaction.class) instanceof Invitation) {
                return error(invitation, PartyInvitationRequestError.Error.ALREADY_IN_PARTY);
            }

            return error(invitation, PartyInvitationRequestError.Error.NOT_FOUND);
        }

        final ExplorationMap map = invitation.initiator().map();

        if (map == null || !map.equals(invitation.target().map())) {
            return error(invitation, PartyInvitationRequestError.Error.ALREADY_IN_PARTY);
        }

        return true;
    }

    /**
     * Send error
     */
    private boolean error(Invitation invitation, PartyInvitationRequestError.Error error) {
        invitation.initiator().send(new PartyInvitationRequestError(error));

        return false;
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
    public @Nullable Interaction start() {
        return null;
    }

    @Override
    public void stop() {

    }

    @Override
    public void send(Object packet) {

    }
}
