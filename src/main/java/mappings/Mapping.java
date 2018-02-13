package mappings;

import beans.DeliveryExec;
import beans.Order;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by astha.a on 12/02/18.
 */
public interface Mapping {
    public HashMap<Order, DeliveryExec> getMapping(ArrayList<Order> orders, ArrayList<DeliveryExec> deliveryExec);
}
