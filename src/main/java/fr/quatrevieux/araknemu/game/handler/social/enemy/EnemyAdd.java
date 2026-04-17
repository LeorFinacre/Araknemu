package fr.quatrevieux.araknemu.game.handler.social.enemy;

import fr.quatrevieux.araknemu.core.network.exception.ErrorPacket;
import fr.quatrevieux.araknemu.data.living.entity.social.Friend;
import fr.quatrevieux.araknemu.game.account.GameAccount;
import fr.quatrevieux.araknemu.game.handler.AbstractLoggedPacketHandler;
import fr.quatrevieux.araknemu.game.handler.social.exception.FriendException;
import fr.quatrevieux.araknemu.game.player.GamePlayer;
import fr.quatrevieux.araknemu.game.player.PlayerService;
import fr.quatrevieux.araknemu.game.social.friend.FriendService;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.social.enemy.EnemyAddRequest;
import fr.quatrevieux.araknemu.network.game.out.social.FriendEnemyErrorCodes;
import fr.quatrevieux.araknemu.network.game.out.social.enemy.EnemyAddError;
import fr.quatrevieux.araknemu.network.game.out.social.enemy.EnemyAddResponse;

import java.util.NoSuchElementException;

public class EnemyAdd extends AbstractLoggedPacketHandler<EnemyAddRequest> {
    private final FriendService friendService;
    private final PlayerService playerService;

    public EnemyAdd(FriendService friendService, PlayerService playerService) {
        this.friendService = friendService;
        this.playerService = playerService;
    }

    @Override
    protected void handle(GameSession session, GameAccount account, EnemyAddRequest packet) throws Exception {
        try {
            GamePlayer joueur = playerService.get(packet.pseudo());
            Friend f = new Friend(account.id(), joueur.account().serverId(), joueur.account().id(), true);
            if(!friendService.has(f)) {
                if(account.id() != joueur.account().id()) {
                    try {
                        friendService.add(account, f, true);
                        session.send(new EnemyAddResponse(packet.pseudo()));
                    } catch (FriendException e) {
                        throw new ErrorPacket(new EnemyAddError(FriendEnemyErrorCodes.FULL_LIST), e);
                    }
                } else {
                    throw new ErrorPacket(new EnemyAddError(FriendEnemyErrorCodes.EGOCENTRIC));
                }
            }
            else {
                throw new ErrorPacket(new EnemyAddError(FriendEnemyErrorCodes.ALREADY_ADDED));
            }
        } catch (NoSuchElementException e) {
            throw new ErrorPacket(new EnemyAddError(FriendEnemyErrorCodes.NOT_FOUND), e);
        }
    }

    @Override
    public Class<EnemyAddRequest> packet() {
        return EnemyAddRequest.class;
    }
}