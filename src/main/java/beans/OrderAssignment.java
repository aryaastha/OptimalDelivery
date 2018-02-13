package beans;

/**
 * Created by astha.a on 13/02/18.
 */
public class OrderAssignment {
    Order order;
    DeliveryExec deliveryExec;

    public OrderAssignment(Order order, DeliveryExec deliveryExec) {
        this.order = order;
        this.deliveryExec = deliveryExec;
    }

    public Order getOrder() {
        return order;
    }

    public DeliveryExec getDeliveryExec() {
        return deliveryExec;
    }
}
