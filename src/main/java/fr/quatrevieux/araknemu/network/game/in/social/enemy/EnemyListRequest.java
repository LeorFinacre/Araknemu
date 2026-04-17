package fr.quatrevieux.araknemu.network.game.in.social.enemy;

import fr.quatrevieux.araknemu.core.network.parser.Packet;
import fr.quatrevieux.araknemu.core.network.parser.ParsePacketException;
import fr.quatrevieux.araknemu.core.network.parser.SinglePacketParser;
import org.checkerframework.common.value.qual.MinLen;

public class EnemyListRequest implements Packet {


    public static final class Parser implements SinglePacketParser<EnemyListRequest> {

        @Override
        public EnemyListRequest parse(String input) throws ParsePacketException {
            return new EnemyListRequest();
        }

        @Override
        public @MinLen(2) String code() {
            return "iL";
        }
    }
}