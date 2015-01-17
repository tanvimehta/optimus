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
    private long user_id;

    @Column(name="building", nullable = true)
    private String building;

    @Column(name="floor", nullable = true)
    private long floor;

    @Column(name="x_coordinate", nullable = true)
    private long x_coordinate;

    @Column(name="y_coordinate", nullable = true)
    private long y_coordinate;

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

    public long getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(long x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public long getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(long y_coordinate) {
        this.y_coordinate = y_coordinate;
    }
}
