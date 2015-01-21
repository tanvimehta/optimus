package com.uplan.resources;

import com.codahale.metrics.annotation.Timed;
import com.uplan.core.ResetPasswordToken;
import com.uplan.core.User;
import com.uplan.db.ResetPasswordTokenDAO;
import com.uplan.db.UserDAO;
import com.uplan.utils.EmailSender;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

/**
 * Created by tanvimehta on 15-01-16.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO userDAO;
    private ResetPasswordTokenDAO resetPasswordTokenDAO;

    public UserResource(UserDAO userDAO, ResetPasswordTokenDAO resetPasswordTokenDAO) {
        this.userDAO = userDAO;
        this.resetPasswordTokenDAO = resetPasswordTokenDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("login")
    public Optional<User> verifyUser(@QueryParam("password") String password,
                                     @QueryParam("email") String email,
                                     @QueryParam("regid") String regId) {
        if (userDAO.getUserByEmailAndPassword(email, DigestUtils.md5Hex(password).toString()) != null) {
            userDAO.setRegId(email, regId);
        }
        return Optional.fromNullable(userDAO.getUserByEmailAndPassword(email, DigestUtils.md5Hex(password).toString()));
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("signup")
    public Optional<User> createUser(@QueryParam("first") String firstName,
                                     @QueryParam("last") String lastName,
                                     @QueryParam("email") String email,
                                     @QueryParam("password") String password) {
        Optional<User> userOptional = Optional.absent();
        if (!userDAO.verifyUserExists(email)) {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(DigestUtils.md5Hex(password));
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userOptional = Optional.fromNullable(userDAO.persistUser(user));
            EmailSender.sendEmail(email, "Registration Confirmation", "Welcome to the UPlan! Please click on this " +
                    "link to successfully register! - http://104.236.85.199:8080/user/confirm?email=" + email + "&token=" + token);
        }
        return userOptional;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("confirm")
    public String confirmSignUp(@QueryParam("email") String email,
                                     @QueryParam("token") String token) {
        User user = userDAO.getUserByEmail(email);
        if (user.getToken() != null && StringUtils.equals(token, user.getToken())) {
            userDAO.setValid(email);
            return "Congratulations! Registration is complete. Welcome to UPlan!";
        }
        return "Sorry, Registration failed. Please follow the link in the email sent to you or contact us.";

    }
//    @POST
//    @UnitOfWork
//    @Path("createUser")
//    public Optional<User> createUser(User user) {
//        Optional<User> userOptional = Optional.absent();
//        if (!userDAO.verifyUserExists(user.getEmail())) {
//            User newUser = new User();
//            newUser.setFirstName(user.getFirstName());
//            newUser.setLastName(user.getLastName());
//            newUser.setEmail(user.getEmail());
//            newUser.setPassword(DigestUtils.md5Hex(user.getPassword()));
//            newUser.setToken(UUID.randomUUID().toString());
//            userOptional = Optional.fromNullable(userDAO.persistUser(newUser));
//        }
//        return userOptional;
//    }

    @GET
    @Timed
    @UnitOfWork
    @Path("resetPassword")
    public boolean resetPassword(@QueryParam("email") String email) {
        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
            resetPasswordToken.setEmail(email);
            resetPasswordToken.setToken(token);
            resetPasswordTokenDAO.persistResetToken(resetPasswordToken);
            EmailSender.sendEmail(email, "Password Reset", "http://104.236.85.199:8080/user/email=" + email + "&token=" + token);
            return true;
        }
        return false;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("changePassword")
    public boolean changePassword(@QueryParam("email") String email,
                                  @QueryParam("newPassword") String newPassword,
                                  @QueryParam("resetToken") String resetToken) {
        ResetPasswordToken resetPasswordToken = resetPasswordTokenDAO.getLatestResetPasswordToken(email);
        if (resetPasswordToken != null && StringUtils.equals(resetToken, resetPasswordToken.getToken())) {
            User user = userDAO.getUserByEmail(email);
            user.setPassword(DigestUtils.md5Hex(newPassword));
            user.setToken(UUID.randomUUID().toString());
            userDAO.persistUser(user);
            resetPasswordTokenDAO.deleteResetTokens(resetPasswordToken.getResetPasswordId());
            return true;
        }
        return false;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("logout")
    public boolean userLogOut(@QueryParam("password") String password,
                                     @QueryParam("email") String email) {

        if (userDAO.getUserByEmailAndPassword(email, DigestUtils.md5Hex(password).toString()) != null) {
            userDAO.setRegId(email, null);
            return true;
        }
        return false;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("deleteUser")
    public boolean deleteUser(@QueryParam("password") String password,
                              @QueryParam("email") String email) {

        if (userDAO.getUserByEmailAndPassword(email, DigestUtils.md5Hex(password).toString()) != null) {
            userDAO.deleteUser(email);
            return true;
        }
        return false;
    }
}

