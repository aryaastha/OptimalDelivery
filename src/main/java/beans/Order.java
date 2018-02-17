package beans;

/**
 * Created by astha.a on 08/02/18.
 */
public class Order {
    private Integer orderId;
    private Restaurant restaurant;
    private Double orderedTime;

    public Order(Integer orderId, Restaurant res, Double orderedTime) {
        this.orderId = orderId;
        this.restaurant = res;
        this.orderedTime = orderedTime;
    }

    public Order() {}

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Double getOrderedTime() {
        return orderedTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (!getOrderId().equals(order.getOrderId())) return false;
        if (!getRestaurant().equals(order.getRestaurant())) return false;
        return getOrderedTime().equals(order.getOrderedTime());

    }

    @Override
    public int hashCode() {
        int result = getOrderId().hashCode();
        result = 31 * result + getRestaurant().hashCode();
        result = 31 * result + getOrderedTime().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", restaurant=" + restaurant +
                ", orderedTime=" + orderedTime +
                '}';
    }
}
