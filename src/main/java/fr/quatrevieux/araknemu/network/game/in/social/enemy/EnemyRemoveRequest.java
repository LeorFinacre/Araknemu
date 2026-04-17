package fr.quatrevieux.araknemu.network.game.in.social.enemy;

import fr.quatrevieux.araknemu.core.network.parser.Packet;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.core.network.parser.SinglePacketParser;
import org.checkerframework.common.value.qual.MinLen;

public class EnemyRemoveRequest implements Packet {
    private final String accountName;

    public EnemyRemoveRequest(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Get the search account name
     */
    public String accountName() {
        return accountName;
    }

    public static final class Parser implements SinglePacketParser<EnemyRemoveRequest> {

        @Override
        public EnemyRemoveRequest parse(String input) throws ParsePacketException {
            return new EnemyRemoveRequest(input.replace("*", ""));
        }

        @Override
        public @MinLen(2) String code() {
            return "iD";
        }
    }
}