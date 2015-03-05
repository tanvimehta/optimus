package com.uplan.db;

import com.uplan.core.Event;
import com.uplan.core.User;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddharthzaveri on 15-03-04.
 */
public class EventDAO extends AbstractDAO<Event> {

    public EventDAO(SessionFactory factory) {
        super(factory);
    }

    public List<Event> getEventsByUser(long user) {
        List<Event> events = list(namedQuery("com.uplan.core.Event.getEventsByUser").setBigInteger("user", BigInteger.valueOf(user)));
        return events;
    }

    public List<Event> getEventsByEventId(long event_id) {
        List<Event> events = list(namedQuery("com.uplan.core.Event.getEventsById").setBigInteger("event_id", BigInteger.valueOf(event_id)));
        return events;
    }

    public List<Event> getAllEvents() {
        List<Event> events = list(namedQuery("com.uplan.core.Event.getAllEvents"));
        return events;
    }

    public void deleteEventById(long event_id) {
        namedQuery("com.uplan.core.Event.deleteEventById").setBigInteger("event_id", BigInteger.valueOf(event_id)).executeUpdate();
    }

    public void setResponse(long response, long event_id, long user) {
        namedQuery("com.uplan.core.Event.setResponse").setBigInteger("event_id", BigInteger.valueOf(event_id))
                .setBigInteger("user", BigInteger.valueOf(user))
                .setBigInteger("response", BigInteger.valueOf(response)).executeUpdate();
    }

    public Event persistEvent(Event event) {
        return this.persist(event);
    }
}