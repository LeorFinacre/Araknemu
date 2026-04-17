package fr.quatrevieux.araknemu.data.living.repository.social.friend;

import fr.quatrevieux.araknemu.core.dbal.repository.MutableRepository;
import fr.quatrevieux.araknemu.core.dbal.repository.RepositoryException;
import fr.quatrevieux.araknemu.data.living.entity.social.Friend;
import fr.quatrevieux.araknemu.game.account.GameAccount;
import fr.quatrevieux.araknemu.game.handler.social.exception.FriendException;

import java.util.Collection;

public interface FriendRepository extends MutableRepository<Friend> {

    /**
     * Add a friend
     * If the entity is not found, the given entity (parameter) is returned
     * No exceptions is thrown when entity is not found
     *
     * @param entity The entity to find (used as primary key criteria)
     *
     * @return The database entity if found, or the given entity if not
     * @throws RepositoryException When a DBAL error occurs
     */
    public Friend add(GameAccount account, Friend entity, boolean isEnemy) throws FriendException;

    public Collection<Friend> getAll(GameAccount entity, boolean isEnemy);

    public void delete(Friend entity);
}
