package fr.quatrevieux.araknemu.game.handler.emote;

import fr.quatrevieux.araknemu.game.GameBaseCase;
import org.junit.jupiter.api.BeforeEach;

public class SitDownTest extends GameBaseCase {
    private SitDown handler;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();

        handler = new SitDown();
    }
}
