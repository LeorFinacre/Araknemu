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

package fr.quatrevieux.araknemu.game.handler.social.party;

import fr.quatrevieux.araknemu.core.network.exception.ErrorPacket;
import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import fr.quatrevieux.araknemu.game.exploration.exchange.ExchangeFactory;
import fr.quatrevieux.araknemu.game.exploration.map.ExplorationMap;
import fr.quatrevieux.araknemu.game.exploration.party.InvitationFactory;
import fr.quatrevieux.araknemu.game.handler.AbstractExploringPacketHandler;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.social.party.PartyRequest;
import fr.quatrevieux.araknemu.network.game.out.social.party.PartyInvitationRequestError;

/**
 * Handle the party invitation request
 */
public class AskPartyInvitation extends AbstractExploringPacketHandler<PartyRequest> {
    private final InvitationFactory factory;

    public AskPartyInvitation(InvitationFactory factory) {
        this.factory = factory;
    }

    @Override
    public void handle (GameSession session, ExplorationPlayer exploration, PartyRequest packet) throws ErrorPacket {
        final ExplorationMap map = exploration.map();

        if (map == null) {
            throw new ErrorPacket(new PartyInvitationRequestError(PartyInvitationRequestError.Error.NOT_FOUND));
        }

        try {
            // @todo check creature type ?
            packet.id()
                    .map(map::creature)
                    .ifPresent(target -> exploration.interactions().start(factory.create(packet.type(), exploration, target)))
            ;
        } catch (RuntimeException e) {
            throw new ErrorPacket(new PartyInvitationRequestError(PartyInvitationRequestError.Error.NOT_FOUND));
        }

        // @todo handle cell
    }

    @Override
    public Class<PartyRequest> packet () { return PartyRequest.class; }
}