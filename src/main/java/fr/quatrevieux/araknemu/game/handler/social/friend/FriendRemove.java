package fr.quatrevieux.araknemu.game.handler.social.friend;

import fr.quatrevieux.araknemu.data.living.entity.social.Friend;
import fr.quatrevieux.araknemu.game.account.AccountService;
import fr.quatrevieux.araknemu.game.account.GameAccount;
import fr.quatrevieux.araknemu.game.handler.AbstractLoggedPacketHandler;
import fr.quatrevieux.araknemu.game.social.friend.FriendService;
import fr.quatrevieux.araknemu.network.game.GameSession;
import fr.quatrevieux.araknemu.network.game.in.social.friend.FriendRemoveRequest;
import fr.quatrevieux.araknemu.network.game.out.social.friend.FriendRemoveResponse;

public class FriendRemove extends AbstractLoggedPacketHandler<FriendRemoveRequest> {
    private final FriendService friendService;
    private final AccountService accountService;

    public FriendRemove(FriendService friendService, AccountService accountService) {
        this.friendService = friendService;
        this.accountService = accountService;
    }

    @Override
    protected void handle(GameSession session, GameAccount account, FriendRemoveRequest packet) throws Exception {
        GameAccount friendAccount = accountService.findByPseudo(packet.accountName()).get();
        Friend f = new Friend(account.id(), account.serverId(), friendAccount.id(), false);
        friendService.remove(f);
        session.send(new FriendRemoveResponse(true));
    }

    @Override
    public Class<FriendRemoveRequest> packet() {
        return FriendRemoveRequest.class;
    }
}
