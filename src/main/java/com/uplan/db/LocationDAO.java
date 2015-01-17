package com.uplan.db;

import com.uplan.core.Location;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.math.BigInteger;

/**
 * Created by tanvimehta on 15-01-17.
 */
public class LocationDAO extends AbstractDAO<Location> {

    public LocationDAO(SessionFactory factory) {
        super(factory);
    }

    public Location persistLocation(Location location) {
        return this.persist(location);
    }

    public Location getLocation(long userId) {
        return uniqueResult(namedQuery("com.uplan.core.Location.getLocation").
                setBigInteger("user_id", BigInteger.valueOf(userId)));
    }

    public void updateLocation(long floor, String building, long x, long y, long userId) {
        namedQuery("com.uplan.core.Location.updateLocation").setBigInteger("floor", BigInteger.valueOf(floor)).
                setString("building", building).setBigInteger("x_coordinate", BigInteger.valueOf(x)).
                setBigInteger("y_coordinate", BigInteger.valueOf(y)).setBigInteger("user_id", BigInteger.valueOf(userId)).
                executeUpdate();
    }
}
