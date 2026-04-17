package fr.quatrevieux.araknemu.game.handler.social.enemy;

import fr.quatrevieux.araknemu.data.living.entity.social.Friend;
import fr.quatrevieux.araknemu.game.account.AccountService;
import fr.quatrevieux.araknemu.game.account.GameAccount;
import fr.quatrevieux.araknemu.game.handler.AbstractLoggedPacketHandler;
import fr.quatrevieux.araknemu.game.social.friend.FriendService;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.social.enemy.EnemyRemoveRequest;
import fr.quatrevieux.araknemu.network.game.out.social.friend.FriendRemoveResponse;

public class EnemyRemove extends AbstractLoggedPacketHandler<EnemyRemoveRequest> {
    private final FriendService friendService;
    private final AccountService accountService;

    public EnemyRemove(FriendService friendService, AccountService accountService) {
        this.friendService = friendService;
        this.accountService = accountService;
    }

    @Override
    protected void handle(GameSession session, GameAccount account, EnemyRemoveRequest packet) throws Exception {
        GameAccount friendAccount = accountService.findByPseudo(packet.accountName()).get();
        Friend f = new Friend(account.id(), account.serverId(), friendAccount.id(), false);
        friendService.remove(f);
        session.send(new FriendRemoveResponse(true));
    }

    @Override
    public Class<EnemyRemoveRequest> packet() {
        return EnemyRemoveRequest.class;
    }
}
