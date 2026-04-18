package fr.quatrevieux.araknemu.game.listener.map;

import fr.quatrevieux.araknemu.core.event.Listener;
import fr.quatrevieux.araknemu.game.exploration.event.SitDownPositionChanged;
import fr.quatrevieux.araknemu.game.exploration.map.ExplorationMap;
import fr.quatrevieux.araknemu.network.game.out.emote.SitDownEmote;

public class SendPlayerChangeSitDownPosition implements Listener<SitDownPositionChanged> {
    private final ExplorationMap map;

    public SendPlayerChangeSitDownPosition(ExplorationMap map) {
        this.map = map;
    }

    @Override
    public void on(SitDownPositionChanged event) {
        map.send(new SitDownEmote(event.player(), event.player().isSitted()));
    }

    @Override
    public Class<SitDownPositionChanged> event() {
        return SitDownPositionChanged.class;
    }
}
