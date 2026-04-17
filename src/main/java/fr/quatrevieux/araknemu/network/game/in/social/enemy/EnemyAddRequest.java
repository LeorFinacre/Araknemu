package fr.quatrevieux.araknemu.network.game.in.social.enemy;

import fr.quatrevieux.araknemu.core.network.parser.Packet;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.core.network.parser.SinglePacketParser;
import org.checkerframework.common.value.qual.MinLen;

public class EnemyAddRequest implements Packet {
    private final String pseudo;

    public EnemyAddRequest(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Get the search pseudo
     */
    public String pseudo() {
        return pseudo;
    }

    public static final class Parser implements SinglePacketParser<EnemyAddRequest> {

        @Override
        public EnemyAddRequest parse(String input) throws ParsePacketException {
            return new EnemyAddRequest(input.replace("%", ""));
        }

        @Override
        public @MinLen(2) String code() {
            return "iA";
        }
    }
}