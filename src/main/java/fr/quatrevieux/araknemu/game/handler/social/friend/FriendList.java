package fr.quatrevieux.araknemu.game.handler.social.friend;

import fr.quatrevieux.araknemu.data.living.entity.account.Account;
import fr.quatrevieux.araknemu.data.living.entity.social.Friend;
import fr.quatrevieux.araknemu.game.account.AccountService;
import fr.quatrevieux.araknemu.game.account.GameAccount;
import fr.quatrevieux.araknemu.game.handler.AbstractLoggedPacketHandler;
import fr.quatrevieux.araknemu.game.player.PlayerService;
import fr.quatrevieux.araknemu.game.social.friend.FriendService;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.social.friend.FriendListRequest;
import fr.quatrevieux.araknemu.network.game.out.social.friend.FriendListResponse;

import java.util.ArrayList;
import java.util.Collection;

public class FriendList extends AbstractLoggedPacketHandler<FriendListRequest> {

    private final FriendService friendService;
    private final AccountService accountService;
    private final PlayerService playerService;

    public FriendList(FriendService friendService, AccountService accountService, PlayerService playerService) {
        this.friendService = friendService;
        this.accountService = accountService;
        this.playerService = playerService;
    }

    @Override
    protected void handle(GameSession session, GameAccount account, FriendListRequest packet) throws Exception {
        Collection<Account> accounts = new ArrayList<>();
        Collection<Friend> friends = friendService.getFriendList(account,false);
        for(Friend friend : friends) {
            accounts.add(accountService.findById(friend.contactId()));
        }
        session.send(new FriendListResponse(accounts, playerService));
    }

    @Override
    public Class<FriendListRequest> packet() {
        return FriendListRequest.class;
    }
}
