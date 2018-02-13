package model;

import beans.Location;
import beans.Restaurant;

import java.util.HashMap;

/**
 * Created by astha.a on 12/02/18.
 */
public class Restaurants {
    private static HashMap<Location, Restaurant> map;
    private static Restaurants instance;

    public Restaurants() {
        map = new HashMap<Location, Restaurant>();
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
        if (map.get(restaurantLocation) == null) {
            synchronized (Restaurants.class) {
                if (map.get(restaurantLocation) == null) {
                    map.put(restaurantLocation, new Restaurant(restaurantLocation));
                }
            }
        }
    }

    public Restaurant getExistingRestaurant(Location restaurantLocation) {
        return map.get(restaurantLocation);
    }
}
