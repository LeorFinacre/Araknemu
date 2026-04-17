package fr.quatrevieux.araknemu.network.game.out.emote;

import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;

public class SitDownEmote {
    private final ExplorationPlayer player;

    public SitDownEmote(ExplorationPlayer player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "eUK" + player.id() + "|" + 1;
    }
}
