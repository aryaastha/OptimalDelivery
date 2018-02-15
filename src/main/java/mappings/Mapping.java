package mappings;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;

import java.util.ArrayList;

/**
 * Created by astha.a on 12/02/18.
 */
public interface Mapping {
    public ArrayList<OrderAssignment> getMapping(ArrayList<Order> orders, ArrayList<DeliveryExec> deliveryExec);
}
