package com.uplan.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.uplan.core.Friend;
import com.uplan.core.User;
import com.uplan.db.FriendDAO;
import com.uplan.db.ResetPasswordTokenDAO;
import com.uplan.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by tanvimehta on 15-01-16.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class FriendResource {
    private UserDAO userDAO;
    private FriendDAO friendDAO;

    public FriendResource(UserDAO userDAO, FriendDAO friendDAO) {
        this.userDAO = userDAO;
        this.friendDAO = friendDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("addFriend")
    public boolean addFriend(@QueryParam("userEmail") String userEmail,
                                     @QueryParam("friendEmail") String friendEmail) {
        Friend friend = new Friend();
        friend.setUser_id(userDAO.getUserByEmail(userEmail).getUser_id());
        friend.setFriend_id(userDAO.getUserByEmail(friendEmail).getUser_id());

        if (!friendDAO.verifyFriendExists(friend)) {
            Optional.fromNullable(friendDAO.persistFriend(friend));
            Friend invertedFriend = new Friend();
            invertedFriend.setUser_id(friend.getFriend_id());
            invertedFriend.setFriend_id(friend.getUser_id());
            Optional.fromNullable(friendDAO.persistFriend(invertedFriend));
            return true;
        }
        return false;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("removeFriend")
    public boolean removeFriend(@QueryParam("userEmail") String userEmail,
                                             @QueryParam("friendEmail") String friendEmail) {
        Friend friend = new Friend();
        friend.setUser_id(userDAO.getUserByEmail(userEmail).getUser_id());
        friend.setFriend_id(userDAO.getUserByEmail(friendEmail).getUser_id());

        if (friendDAO.verifyFriendExists(friend)) {
            friendDAO.deleteFriend(friend.getUser_id(), friend.getFriend_id());
            friendDAO.deleteFriend(friend.getFriend_id(), friend.getUser_id());
            return true;
        }
        return false;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("getFriends")
    public List<User> getFriends(@QueryParam("userEmail") String userEmail) {
        List<User> friends = new ArrayList<User>();
        Long userId = userDAO.getUserByEmail(userEmail).getUser_id();

        return friendDAO.findAll(userDAO, userId);
    }
}
