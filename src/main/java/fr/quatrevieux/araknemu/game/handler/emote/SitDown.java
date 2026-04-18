package fr.quatrevieux.araknemu.game.handler.emote;

import fr.quatrevieux.araknemu.core.network.exception.ErrorPacket;
import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import fr.quatrevieux.araknemu.game.handler.AbstractExploringPacketHandler;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.emote.SetSitDownRequest;
import fr.quatrevieux.araknemu.network.game.out.basic.Noop;

public class SitDown extends AbstractExploringPacketHandler<SetSitDownRequest> {
    @Override
    protected void handle(GameSession session, ExplorationPlayer exploration, SetSitDownRequest packet) throws Exception {
        if (!exploration.player().restrictions().canMoveAllDirections()) {
            throw new ErrorPacket(new Noop());
        }
        exploration.setSitDownPosition(!exploration.isSitted());
    }

    @Override
    public Class<SetSitDownRequest> packet() {
        return SetSitDownRequest.class;
    }
}
