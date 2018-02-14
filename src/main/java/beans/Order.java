package beans;

/**
 * Created by astha.a on 08/02/18.
 */
public class Order {
    Integer orderId;
    Restaurant restaurant;
    Double orderedTime;

    public Order(Integer orderId, Restaurant res, Double orderedTime){
        this.orderId = orderId;
        this.restaurant = res;
        this.orderedTime = orderedTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Double getOrderedTime() {
        return orderedTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
