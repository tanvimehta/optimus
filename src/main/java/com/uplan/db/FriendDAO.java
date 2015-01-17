package com.uplan.db;

import com.uplan.core.Friend;
import com.uplan.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanvimehta on 15-01-16.
 */
public class FriendDAO extends AbstractDAO<Friend> {

    public FriendDAO(SessionFactory factory) {
        super(factory);
    }

    public void deleteFriend(long userId, long friendId) {
        namedQuery("com.uplan.core.Friend.deleteFriend").setBigInteger("user_id", BigInteger.valueOf(userId)).
                setBigInteger("friend_id", BigInteger.valueOf(friendId)).executeUpdate();
    }

    public boolean verifyFriendExists(Friend friend) {
        return getAllFriendOfUser(friend.getUser_id(), friend.getFriend_id()) != null;
    }

    public Friend getAllFriendOfUser(Long userId, Long friendId) {
        return uniqueResult(namedQuery("com.uplan.core.Friend.verifyFriendExist").setBigInteger("user_id", BigInteger.valueOf(userId)).setBigInteger("friend_id", BigInteger.valueOf(friendId)));
    }

    public Friend persistFriend(Friend friend) {
        return this.persist(friend);
    }

    public List<User> findAll(UserDAO userDAO, Long userId) {
        List<Friend> friendPairs = list(namedQuery("com.uplan.core.Friend.findAll").setBigInteger("user_id", BigInteger.valueOf(userId)));
        List<User> friends = new ArrayList<User>();
        for (Friend friendPair: friendPairs) {
            User user = userDAO.getUserById(friendPair.getFriend_id());
            friends.add(user);
        }
        return friends;
    }
}
