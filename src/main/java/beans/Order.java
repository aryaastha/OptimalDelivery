package beans;

/**
 * Created by astha.a on 08/02/18.
 */
public class Order {
    Restaurant restaurant;
    Double orderedTime;

    public Order(Restaurant res, Double orderedTime){
        this.restaurant = res;
        this.orderedTime = orderedTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Double getOrderedTime() {
        return orderedTime;
    }
}
