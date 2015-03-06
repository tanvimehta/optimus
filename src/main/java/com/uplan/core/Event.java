package com.uplan.core;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by siddharthzaveri on 15-03-04.
 */

@Entity
@Table(name = "event")
@NamedQueries({
        @NamedQuery(
                name = "com.uplan.core.Event.getAllEvents",
                query = "SELECT event FROM Event event"
        ),
        @NamedQuery(
                name = "com.uplan.core.Event.getEventsById",
                query = "SELECT event FROM Event event where event_id = :event_id"
        ),
        @NamedQuery(
                name = "com.uplan.core.Event.deleteEventById",
                query = "DELETE FROM Event where event_id = :event_id"
        ),
        @NamedQuery(
                name = "com.uplan.core.Event.getEventsByUser",
                query = "SELECT event FROM Event event where user = :user"
        ),
        @NamedQuery(
                name = "com.uplan.core.Event.setResponse",
                query = "UPDATE Event event SET response = :response where user = :user and event_id = :event_id"
        )
})
public class Event  {
    public long getEvent_user_pair() {
        return event_user_pair;
    }

    public void setEvent_user_pair(long event_user_pair) {
        this.event_user_pair = event_user_pair;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long event_user_pair;

    @Column(name="event_id", nullable = false)
    private long event_id;

    @Column(name="user", nullable = false)
    private long user;

    @Column(name="creator", nullable = false)
    private Boolean creator;

    @Column(name="response", nullable = false)
    private Boolean response;

    @Column(name="from_time", nullable = false)
    private float from_time; // Format hh.mm

    @Column(name="to_time", nullable = false)
    private float to_time; // Format hh.mm

    @Column(name="location", nullable = false)
    private String location;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="from_date", nullable = false)
    private String from_date;

    @Column(name="to_date", nullable = false)
    private String to_date;

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public Boolean getCreator() {
        return creator;
    }

    public void setCreator(Boolean creator) {
        this.creator = creator;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }

    public float getFrom_time() {
        return from_time;
    }

    public void setFrom_time(float from_time) {
        this.from_time = from_time;
    }

    public float getTo_time() {
        return to_time;
    }

    public void setTo_time(float to_time) {
        this.to_time = to_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }
}
