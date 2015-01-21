package com.uplan.core;

import javax.persistence.*;

/**
 * Created by tanvimehta on 15-01-16.
 */
@Entity
@Table(name = "friends")
@NamedQueries({
        @NamedQuery(
                name = "com.uplan.core.Friend.deleteFriend",
                query = "DELETE FROM Friend where user_id = :user_id and friend_id = :friend_id"
        ),
        @NamedQuery(
                name = "com.uplan.core.Friend.verifyFriendExist",
                query = "SELECT friend FROM Friend friend where user_id = :user_id and friend_id = :friend_id"
        ),
        @NamedQuery(
                name = "com.uplan.core.Friend.findAll",
                query = "SELECT friend FROM Friend friend where user_id = :user_id"
        )
})
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long friendship_id;

    @Column(name="user_id", nullable = false)
    private long user_id;

    @Column(name="friend_id", nullable = false)
    private long friend_id;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(long friend_id) {
        this.friend_id = friend_id;
    }

    public long getFriendship_id() {
        return friendship_id;
    }

    public void setFriendship_id(long friendship_id) {
        this.friendship_id = friendship_id;
    }
}
