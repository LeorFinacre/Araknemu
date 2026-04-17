package fr.quatrevieux.araknemu.network.game.in.emote;

import fr.quatrevieux.araknemu.core.network.parser.Packet;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.core.network.parser.SinglePacketParser;
import org.checkerframework.common.value.qual.MinLen;

public class SetSitDownRequest implements Packet {

    public static final class Parser implements SinglePacketParser<SetSitDownRequest> {
        @Override
        public SetSitDownRequest parse(String input) throws ParsePacketException {
            return new SetSitDownRequest();
        }

        @Override
        public @MinLen(2) String code() {
            return "eU";
        }
    }
}
