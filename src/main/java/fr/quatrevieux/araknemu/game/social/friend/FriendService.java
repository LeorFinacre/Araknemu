package fr.quatrevieux.araknemu.game.social.friend;

import fr.quatrevieux.araknemu.core.event.EventsSubscriber;
import fr.quatrevieux.araknemu.core.event.Listener;
import fr.quatrevieux.araknemu.data.living.entity.social.Friend;
import fr.quatrevieux.araknemu.data.living.repository.social.friend.FriendRepository;
import fr.quatrevieux.araknemu.game.account.GameAccount;
import fr.quatrevieux.araknemu.game.handler.social.exception.FriendException;

import java.util.Collection;

public class FriendService implements EventsSubscriber {
    private final FriendRepository repository;

    public FriendService(FriendRepository repository) {
        this.repository = repository;
    }

    public Collection<Friend> getFriendList(GameAccount account, boolean isEnemy) {
        return repository.getAll(account, isEnemy);
    }

    public Friend add(GameAccount account, Friend friend, boolean isEnemy) throws FriendException {
        return repository.add(account, friend, isEnemy);
    }

    public boolean has(Friend friend) {
        return repository.has(friend);
    }

    public void remove(Friend friend) { repository.delete(friend); }

    @Override
    public Listener[] listeners() {
        return new Listener[0];
    }
}
