package fr.quatrevieux.araknemu.network.game.out.emote;

import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;

public class SitDownEmote {
    private final ExplorationPlayer player;
    boolean emoteActivated;

    public SitDownEmote(ExplorationPlayer player, boolean emoteActivated) {
        this.player = player;
        this.emoteActivated = emoteActivated;
    }

    @Override
    public String toString() {
        return "eUK" + player.id() + "|" + (emoteActivated ? "1" : "0");
    }
}
