package com.uplan.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.uplan.core.Content;
import com.uplan.core.Event;
import com.uplan.core.ResetPasswordToken;
import com.uplan.core.User;
import com.uplan.db.EventDAO;
import com.uplan.db.UserDAO;
import com.uplan.service.POST2GCM;
import io.dropwizard.hibernate.UnitOfWork;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by siddharthzaveri on 15-03-04.
 */
@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {
    private UserDAO userDAO;
    private EventDAO eventDAO;
    static long EVENT_ID = 0;
    private final String API_KEY = "AIzaSyAm43w75goT3wWIxFoxA1i7MdsV_Q8CGSY";

    public EventResource(UserDAO userDAO, EventDAO eventDAO) {
        this.userDAO = userDAO;
        this.eventDAO = eventDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("create")
    public Optional<Event> createEvent(@QueryParam("friends") String friends,
                                     @QueryParam("email") String email,
                                     @QueryParam("from_time") String from_time,
                                     @QueryParam("to_time") String to_time,
                                     @QueryParam("location") String location,
                                     @QueryParam("name") String name) {
        Optional<Event> eventOptional = Optional.absent();
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            return null;
        }
        EVENT_ID++;
        /* Add creator */
        Event event = new Event();
        event.setCreator(true);
        event.setEvent_id(EVENT_ID);
        event.setFrom_time(Float.parseFloat(from_time));
        event.setTo_time(Float.parseFloat(to_time));
        event.setLocation(location);
        event.setName(name);
        event.setResponse(true);
        event.setUser(user.getUser_id());
        eventOptional = Optional.fromNullable(eventDAO.persistEvent(event));

        /* Add each invitee with default response of false */
        String friend_list[] = friends.split(";");
        for (String friend : friend_list) {
            Optional<Event> tempEventOptional = Optional.absent();
            Event tempEvent = new Event();
            User tmpUser = userDAO.getUserByEmail(friend);
            if (tmpUser == null) {
                continue;
            }
            tempEvent.setUser(tmpUser.getUser_id());
            tempEvent.setCreator(false);
            tempEvent.setEvent_id(EVENT_ID);
            tempEvent.setFrom_time(Float.parseFloat(from_time));
            tempEvent.setTo_time(Float.parseFloat(to_time));
            tempEvent.setLocation(location);
            tempEvent.setName(name);
            tempEvent.setResponse(false);
            tempEventOptional = Optional.fromNullable(eventDAO.persistEvent(tempEvent));

            Content content = new Content();
            content.addRegId(tmpUser.getReg_id());
            content.createData("getResponse", name + ";" + user.getFirstName() + ";" + EVENT_ID);
            POST2GCM.post(API_KEY, content);
        }

        return eventOptional;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("getEvents")
    public List<Event> getEvents(@QueryParam("email") String email) {
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            return null;
        }
        Long userId = user.getUser_id();
        return eventDAO.getEventsByUser(userId);
    }


    @GET
    @Timed
    @UnitOfWork
    @Path("getInvitees")
    public List<User> getInvitees(@QueryParam("event_id") String event_id) {
        Long eventId = Long.parseLong(event_id);
        List<Event> events = eventDAO.getEventsByEventId(eventId);
        List<User> invitees = new ArrayList<User>();
        for (Event event : events) {
            User invitee = userDAO.getUserById(event.getUser());
            if (invitee != null) {
                invitees.add(invitee);
            }
        }
        return invitees;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("deleteEvent")
    public Boolean deleteEvent(@QueryParam("event_id") String event_id) {
        Long eventId = Long.parseLong(event_id);
        eventDAO.deleteEventById(eventId);
        return true;
    }


    @GET
    @Timed
    @UnitOfWork
    @Path("setResponse")
    public Boolean setResponse(@QueryParam("event_id") String event_id,
                               @QueryParam("email") String email,
                               @QueryParam("response") String response) {
        Long eventId = Long.parseLong(event_id);
        long responseLong = 0;
        if (response.equals("true")) {
            responseLong = 1;
        }

        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            return false;
        }
        Long userId = user.getUser_id();
        eventDAO.setResponse(responseLong, eventId, userId);
        return true;
    }
}

