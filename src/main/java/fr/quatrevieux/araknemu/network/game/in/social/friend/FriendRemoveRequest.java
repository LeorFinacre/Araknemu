package fr.quatrevieux.araknemu.network.game.in.social.friend;

import fr.quatrevieux.araknemu.core.network.parser.Packet;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.core.network.parser.SinglePacketParser;
import org.checkerframework.common.value.qual.MinLen;

public class FriendRemoveRequest implements Packet {
    private final String accountName;

    public FriendRemoveRequest(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Get the search account name
     */
    public String accountName() {
        return accountName;
    }

    public static final class Parser implements SinglePacketParser<FriendRemoveRequest> {

        @Override
        public FriendRemoveRequest parse(String input) throws ParsePacketException {
            return new FriendRemoveRequest(input.replace("*", ""));
        }

        @Override
        public @MinLen(2) String code() {
            return "FD";
        }
    }
}
