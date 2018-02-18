package mappings;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;

import java.util.List;

/**
 * Created by astha.a on 12/02/18.
 */
public interface Mapping {
    public List<OrderAssignment> getMapping(List<Order> orders, List<DeliveryExec> deliveryExec) throws Exception;
}
