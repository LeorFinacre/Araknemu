package fr.quatrevieux.araknemu.game.handler.social.enemy;

import fr.quatrevieux.araknemu.data.living.entity.account.Account;
import fr.quatrevieux.araknemu.data.living.entity.social.Friend;
import fr.quatrevieux.araknemu.game.account.AccountService;
import fr.quatrevieux.araknemu.game.account.GameAccount;
import fr.quatrevieux.araknemu.game.handler.AbstractLoggedPacketHandler;
import fr.quatrevieux.araknemu.game.player.PlayerService;
import fr.quatrevieux.araknemu.game.social.friend.FriendService;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.social.enemy.EnemyListRequest;
import fr.quatrevieux.araknemu.network.game.out.social.enemy.EnemyListResponse;

import java.util.ArrayList;
import java.util.Collection;

public class EnemyList extends AbstractLoggedPacketHandler<EnemyListRequest> {

    private final FriendService friendService;
    private final AccountService accountService;
    private final PlayerService playerService;

    public EnemyList(FriendService friendService, AccountService accountService, PlayerService playerService) {
        this.friendService = friendService;
        this.accountService = accountService;
        this.playerService = playerService;
    }

    @Override
    protected void handle(GameSession session, GameAccount account, EnemyListRequest packet) throws Exception {
        Collection<Account> accounts = new ArrayList<>();
        Collection<Friend> friends = friendService.getFriendList(account, true);
        for (Friend friend : friends) {
            accounts.add(accountService.findById(friend.contactId()));
        }
        session.send(new EnemyListResponse(accounts, playerService));
    }

    @Override
    public Class<EnemyListRequest> packet() {
        return EnemyListRequest.class;
    }
}