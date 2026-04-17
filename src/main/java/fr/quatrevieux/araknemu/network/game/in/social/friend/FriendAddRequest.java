package fr.quatrevieux.araknemu.network.game.in.social.friend;

import fr.quatrevieux.araknemu.core.network.parser.Packet;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.core.network.parser.SinglePacketParser;
import org.checkerframework.common.value.qual.MinLen;

public class FriendAddRequest implements Packet {
    private final String pseudo;

    public FriendAddRequest(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Get the search pseudo
     */
    public String pseudo() {
        return pseudo;
    }

    public static final class Parser implements SinglePacketParser<FriendAddRequest> {

        @Override
        public FriendAddRequest parse(String input) throws ParsePacketException {
            return new FriendAddRequest(input.replace("%", ""));
        }

        @Override
        public @MinLen(2) String code() {
            return "FA";
        }
    }
}
