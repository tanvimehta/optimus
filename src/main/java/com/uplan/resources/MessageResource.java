package com.uplan.resources;

import com.codahale.metrics.annotation.Timed;
import com.uplan.core.Content;
import com.uplan.core.Location;
import com.uplan.core.User;
import com.uplan.db.LocationDAO;
import com.uplan.db.UserDAO;
import com.uplan.service.POST2GCM;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by tanvimehta on 15-01-21.
 */
@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

    private final String DELIMITER = ";";
    private UserDAO userDAO;
    private final String API_KEY = "AIzaSyAm43w75goT3wWIxFoxA1i7MdsV_Q8CGSY";
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationResource.class);

    public MessageResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("sendMessage")
    public boolean locateFriend(@QueryParam("userEmail") String userEmail,
                                 @QueryParam("friendEmail") String friendEmail,
                                 @QueryParam("message") String message) {
        User friend = userDAO.getUserByEmail(friendEmail);

        Content content = new Content();
        content.addRegId(friend.getReg_id());
        content.createData("message", userEmail + ";" + message);

        POST2GCM.post(API_KEY, content);
        return true;
    }
}
