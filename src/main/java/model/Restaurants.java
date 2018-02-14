package model;

import beans.Location;
import beans.Restaurant;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by astha.a on 12/02/18.
 */
public class Restaurants {
    private static ConcurrentHashMap<Location, Restaurant> map;
    private static Restaurants instance;

    public Restaurants() {
        map = new ConcurrentHashMap<Location, Restaurant>();
    }

    public static Restaurants getInstance() {
        if (map == null) {
            synchronized (Restaurants.class) {
                if (map == null) {
                    instance = new Restaurants();
                }
            }
        }
        return instance;
    }

    public void addNewRestaurant(Location restaurantLocation) {
        map.putIfAbsent(restaurantLocation, new Restaurant(restaurantLocation));
    }

    public Restaurant getExistingRestaurant(Location restaurantLocation) {
        return map.get(restaurantLocation);
    }
}
