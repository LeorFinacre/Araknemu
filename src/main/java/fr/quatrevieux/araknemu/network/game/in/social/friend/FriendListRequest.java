package fr.quatrevieux.araknemu.network.game.in.social.friend;

import fr.quatrevieux.araknemu.core.network.parser.Packet;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.core.network.parser.SinglePacketParser;
import org.checkerframework.common.value.qual.MinLen;

public class FriendListRequest implements Packet {


    public static final class Parser implements SinglePacketParser<FriendListRequest> {

        @Override
        public FriendListRequest parse(String input) throws ParsePacketException {
            return new FriendListRequest();
        }

        @Override
        public @MinLen(2) String code() {
            return "FL";
        }
    }
}
