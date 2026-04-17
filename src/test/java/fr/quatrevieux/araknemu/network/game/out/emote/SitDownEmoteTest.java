package fr.quatrevieux.araknemu.network.game.out.emote;

import fr.arakne.utils.maps.constant.Direction;
import fr.quatrevieux.araknemu.core.di.ContainerException;
import fr.quatrevieux.araknemu.game.GameBaseCase;
import fr.quatrevieux.araknemu.game.exploration.ExplorationPlayer;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SitDownEmoteTest extends GameBaseCase {
    @Test
    void generate() throws SQLException, ContainerException {
        ExplorationPlayer exploration = explorationPlayer();

        assertEquals("eUK6|1", new SitDownEmote(exploration).toString());
    }
}
