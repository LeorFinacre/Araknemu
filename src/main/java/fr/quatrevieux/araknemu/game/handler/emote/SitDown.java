package fr.quatrevieux.araknemu.game.handler.emote;

import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import fr.quatrevieux.araknemu.game.handler.AbstractExploringPacketHandler;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.emote.SetSitDownRequest;

public class SitDown extends AbstractExploringPacketHandler<SetSitDownRequest> {
    @Override
    protected void handle(GameSession session, ExplorationPlayer exploration, SetSitDownRequest packet) throws Exception {
        exploration.setSitDownPosition();
    }

    @Override
    public Class<SetSitDownRequest> packet() {
        return SetSitDownRequest.class;
    }
}
