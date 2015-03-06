package com.uplan.core;

import javax.persistence.*;

/**
 * Created by tanvimehta on 15-01-17.
 */
@Entity
@Table(name = "location")
@NamedQueries({
        @NamedQuery(
                name = "com.uplan.core.Location.getLocation",
                query = "SELECT location FROM Location location where user_id = :user_id"
        ),
        @NamedQuery(
                name = "com.uplan.core.Location.updateLocation",
                query = "UPDATE Location location SET floor = :floor, building = :building, " +
                        "x_coordinate = :x_coordinate, y_coordinate = :y_coordinate where user_id = :user_id"
        )
})
public class Location {

    @Id
    private long location_id;

    @Column(name="user_id", nullable = true)
    private long user_id;

    @Column(name="building", nullable = true)
    private String building;

    @Column(name="floor", nullable = true)
    private long floor;

    @Column(name="x_coordinate", nullable = true)
    private float x_coordinate;

    @Column(name="y_coordinate", nullable = true)
    private float y_coordinate;

    @Column(name="accuracy", nullable = true)
    private float accuracy;

    @Column(name="bearing", nullable = true)
    private float bearing;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public long getFloor() {
        return floor;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }

    public float getX_coordinate() {
        return x_coordinate;
    }

    public float getY_coordinate() {
        return y_coordinate;
    }

    public void setX_coordinate(float x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public void setY_coordinate(float y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }
}
