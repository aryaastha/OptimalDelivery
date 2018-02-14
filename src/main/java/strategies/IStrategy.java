package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by astha.a on 14/02/18.
 */
public interface IStrategy {
    public HashMap<Order, DeliveryExec> getFinalAssignment(LinkedHashMap<OrderAssignment, Double> allCombinationScoreList);
}
