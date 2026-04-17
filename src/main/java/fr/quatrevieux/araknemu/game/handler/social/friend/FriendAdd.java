package fr.quatrevieux.araknemu.game.handler.social.friend;

import fr.quatrevieux.araknemu.core.network.exception.ErrorPacket;
import fr.quatrevieux.araknemu.data.living.entity.social.Friend;
import fr.quatrevieux.araknemu.game.account.GameAccount;
import fr.quatrevieux.araknemu.game.handler.AbstractLoggedPacketHandler;
import fr.quatrevieux.araknemu.game.handler.social.exception.FriendException;
import fr.quatrevieux.araknemu.game.player.GamePlayer;
import fr.quatrevieux.araknemu.game.player.PlayerService;
import fr.quatrevieux.araknemu.game.social.friend.FriendService;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.social.friend.FriendAddRequest;
import fr.quatrevieux.araknemu.network.game.out.social.FriendEnemyErrorCodes;
import fr.quatrevieux.araknemu.network.game.out.social.friend.FriendAddError;
import fr.quatrevieux.araknemu.network.game.out.social.friend.FriendAddResponse;

import java.util.NoSuchElementException;

public class FriendAdd extends AbstractLoggedPacketHandler<FriendAddRequest> {
    private final FriendService friendService;
    private final PlayerService playerService;

    public FriendAdd(FriendService friendService, PlayerService playerService) {
        this.friendService = friendService;
        this.playerService = playerService;
    }

    @Override
    protected void handle(GameSession session, GameAccount account, FriendAddRequest packet) throws Exception {
        try {
            GamePlayer joueur = playerService.get(packet.pseudo());
            Friend f = new Friend(account.id(), joueur.account().serverId(), joueur.account().id(), false);
            if(!friendService.has(f)) {
                if(account.id() != joueur.account().id()) {
                    try {
                        friendService.add(account, f, false);
                        session.send(new FriendAddResponse(packet.pseudo()));
                    } catch (FriendException e) {
                        throw new ErrorPacket(new FriendAddError(FriendEnemyErrorCodes.FULL_LIST), e);
                    }
                } else {
                    throw new ErrorPacket(new FriendAddError(FriendEnemyErrorCodes.EGOCENTRIC));
                }
            }
            else {
                throw new ErrorPacket(new FriendAddError(FriendEnemyErrorCodes.ALREADY_ADDED));
            }
        } catch (NoSuchElementException e) {
            throw new ErrorPacket(new FriendAddError(FriendEnemyErrorCodes.NOT_FOUND), e);
        }
    }

    @Override
    public Class<FriendAddRequest> packet() {
        return FriendAddRequest.class;
    }
}
