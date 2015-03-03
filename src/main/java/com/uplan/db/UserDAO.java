package com.uplan.db;

import com.uplan.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.math.BigInteger;

/**
 * Created by tanvimehta on 15-01-16.
 */
public class UserDAO extends AbstractDAO<User> {

    private final long TRUE = 1;
    public UserDAO(SessionFactory factory) {
        super(factory);
    }

    public User getUserByEmailAndPassword(String email, String password) {
        return uniqueResult(namedQuery("com.uplan.core.User.verifyUser").setString("email", email).setString("password", password));
    }

    public boolean verifyUserExists(String email) {
        return getUserByEmail(email) != null;
    }

    public User getUserByEmail(String email) {
        return uniqueResult(namedQuery("com.uplan.core.User.verifyUserUsingEmail").setString("email", email));
    }

    public User getUserById(Long userId) {
        return uniqueResult(namedQuery("com.uplan.core.User.getUserById").setBigInteger("user_id", BigInteger.valueOf(userId)));
    }

    public void setRegId(String email, String regId) {
        long userId = getUserByEmail(email).getUser_id();
        namedQuery("com.uplan.core.User.setRegId").setBigInteger("user_id", BigInteger.valueOf(userId)).setString("reg_id", regId).executeUpdate();
    }

    public User persistUser(User user) {
        return this.persist(user);
    }

    public void deleteUser(String email) {
        long userId = getUserByEmail(email).getUser_id();
        namedQuery("com.uplan.core.User.deleteUser").setBigInteger("user_id", BigInteger.valueOf(userId)).executeUpdate();
    }

    public void setValid(String email) {
        long userId = getUserByEmail(email).getUser_id();
        namedQuery("com.uplan.core.User.setValid").setBigInteger("user_id", BigInteger.valueOf(userId)).setBigInteger("valid", BigInteger.valueOf(TRUE)).executeUpdate();
    }

    public void setLocPermission(long permission, String email) {
        long userId = getUserByEmail(email).getUser_id();
        namedQuery("com.uplan.core.User.setLocPermission").setBigInteger("user_id", BigInteger.valueOf(userId)).setBigInteger("permission", BigInteger.valueOf(permission)).executeUpdate();
    }
}