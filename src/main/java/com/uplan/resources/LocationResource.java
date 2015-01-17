package com.uplan.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.uplan.core.Location;
import com.uplan.db.LocationDAO;
import com.uplan.db.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;

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
}
