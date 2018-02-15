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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderAssignment)) return false;

        OrderAssignment that = (OrderAssignment) o;

        if (getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null) return false;
        return getDeliveryExec() != null ? getDeliveryExec().equals(that.getDeliveryExec()) : that.getDeliveryExec() == null;

    }

    @Override
    public int hashCode() {
        int result = getOrder() != null ? getOrder().hashCode() : 0;
        result = 31 * result + (getDeliveryExec() != null ? getDeliveryExec().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "The order : {" + order +
                "}\n \t was assigned to \n \t \t deliveryExec :{" + deliveryExec +
                "}\n";
    }
}
