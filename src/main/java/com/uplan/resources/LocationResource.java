package com.uplan.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
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
 * Created by tanvimehta on 15-01-17.
 */
@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
public class LocationResource {
    private UserDAO userDAO;
    private LocationDAO locationDAO;
    private final String API_KEY = "AIzaSyAm43w75goT3wWIxFoxA1i7MdsV_Q8CGSY";
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationResource.class);

    public LocationResource(UserDAO userDAO, LocationDAO locationDAO) {
        this.userDAO = userDAO;
        this.locationDAO = locationDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("addLocation")
    public boolean addLocation(@QueryParam("email") String email,
                             @QueryParam("floor") long floor,
                             @QueryParam("building") String building,
                             @QueryParam("x") long xCoord,
                             @QueryParam("y") long yCoord) {
        long userId = userDAO.getUserByEmail(email).getUser_id();

        if (userId != 0 && locationDAO.getLocation(userId) != null) {
            locationDAO.updateLocation(floor, building, xCoord, yCoord, userId);
            return true;
        } else if (userId != 0){
            Location location = new Location();
            location.setFloor(floor);
            location.setBuilding(building);
            location.setUser_id(userId);
            location.setX_coordinate(xCoord);
            location.setY_coordinate(yCoord);
            locationDAO.persistLocation(location);
            return true;
        }
        return false;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("getLocation")
    public Optional<Location> getLocation(@QueryParam("email") String email) {
        long userId = userDAO.getUserByEmail(email).getUser_id();
        return Optional.fromNullable(locationDAO.getLocation(userId));
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("locateFriend")
    public Location locateFriend(@QueryParam("userEmail") String userEmail,
            @QueryParam("friendEmail") String friendEmail) {
        User friend = userDAO.getUserByEmail(friendEmail);
        if (!friend.isLoc_permission()) {
            return null;
        }

        Content content = new Content();
        content.addRegId(friend.getReg_id());
//        content.addRegId("APA91bHKmGv67k1xO7dnwIpdtL6zQ5Vt55-yrklQ8bMySG1F1-tQ5J9CIQHCNWGirUfNfUHkcMunQ_5U_PdPxQWBsTNeV5-50DI847EA4P9vVkNAjo08hsrbyQYuKrqe5hx8JHq8n8eEH_waAiEBUbhbTZ00z33OW2IvMWYS4OkUBNfirEKO7oM");
        content.createData("getLocation", userEmail);

        POST2GCM.post(API_KEY, content);

        // Remove this once the request handling is fixed
        Location location = new Location();
        location.setBuilding("Bahen");
        location.setFloor(1);
        location.setX_coordinate(123);
        location.setY_coordinate(123);
        return location;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("putLocation")
    public boolean putLocation(@QueryParam("friendEmail") String friendEmail,
                                @QueryParam("building") String building,
                                @QueryParam("floor") String floor,
                                @QueryParam("x") String xCoord,
                                @QueryParam("y") String yCoord) {
        User friend = userDAO.getUserByEmail(friendEmail);

        Content content = new Content();
        content.addRegId(friend.getReg_id());
        content.createData("putLocation", "friendEmail=" + friendEmail + "&building=" + building + "&floor=" + floor +
        "&x=" + xCoord + "&y=" + yCoord);

        long userId = friend.getUser_id();
        if (userId != 0 && locationDAO.getLocation(userId) != null) {
            locationDAO.updateLocation(Long.parseLong(floor), building, Long.parseLong(xCoord), Long.parseLong(yCoord), userId);
        } else if (userId != 0){
            Location location = new Location();
            location.setFloor(Long.parseLong(floor));
            location.setBuilding(building);
            location.setUser_id(userId);
            location.setX_coordinate(Long.parseLong(xCoord));
            location.setY_coordinate(Long.parseLong(yCoord));
            locationDAO.persistLocation(location);
        }

        POST2GCM.post(API_KEY, content);
        return true;
    }
}
