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

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantLocation=" + restaurantLocation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;

        Restaurant that = (Restaurant) o;

        return getRestaurantLocation() != null ? getRestaurantLocation().equals(that.getRestaurantLocation()) : that.getRestaurantLocation() == null;

    }

    @Override
    public int hashCode() {
        return getRestaurantLocation() != null ? getRestaurantLocation().hashCode() : 0;
    }
}
