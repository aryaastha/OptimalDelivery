package beans;

/**
 * Created by astha.a on 08/02/18.
 */
public class Restaurant {
    private Location restaurantLocation;

    public Restaurant(Location restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public Location getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(Location restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantLocation=" + restaurantLocation +
                '}';
    }
}
