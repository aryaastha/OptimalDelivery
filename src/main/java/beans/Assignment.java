package beans;

/**
 * Created by astha.a on 12/02/18.
 */
public class Assignment {
    DeliveryExec deliveryExec;
    Order order;
    Double cost;

    public Double getCost() {
        return cost;
    }

    public Assignment(DeliveryExec deliveryExec, Order order, Double cost) {
        this.deliveryExec = deliveryExec;
        this.order = order;
        this.cost = cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public DeliveryExec getDeliveryExec() {
        return deliveryExec;
    }

    public Order getOrder() {
        return order;
    }
}
