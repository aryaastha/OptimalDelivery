package utils;

import beans.Location;

/**
 * Created by astha.a on 13/02/18.
 */
public class LocationUtils {
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    public static Double distance(Location location1, Location location2) {
        Double deltaLat = toRad(location1.getLatitude() - location2.getLatitude());
        Double deltatLong = toRad(location1.getLongitude() - location2.getLongitude());
        Double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(toRad(location1.getLatitude())) * Math.cos(toRad(location2.getLatitude())) *
                        Math.sin(deltatLong / 2) * Math.sin(deltatLong / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Location.R * c;
    }
}
