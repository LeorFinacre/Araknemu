package fr.quatrevieux.araknemu.network.game.out.emote;

import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;

public class SitDownEmote {
    private final ExplorationPlayer player;
    boolean isSitted;

    public SitDownEmote(ExplorationPlayer player, boolean isSitted) {
        this.player = player;
        this.isSitted = isSitted;
    }

    @Override
    public String toString() {
        return "eUK" + player.id() + "|" + (isSitted ? "1" : "0");
    }
}
