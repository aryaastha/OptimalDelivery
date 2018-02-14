package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;

import java.util.*;

/**
 * Created by astha.a on 14/02/18.
 */
public class GreedyStrategy implements IStrategy {
    public GreedyStrategy() {}

    @Override
    public HashMap<Order, DeliveryExec> getFinalAssignment(LinkedHashMap<OrderAssignment, Double> allCombinationScoreList) {
        HashMap<Order, DeliveryExec> finalAssignment = new HashMap<>();
        List<Map.Entry<OrderAssignment, Double>> list = new LinkedList<Map.Entry<OrderAssignment, Double>>(allCombinationScoreList.entrySet());
        Collections.sort( list, new Comparator<Map.Entry<OrderAssignment, Double>>() {
            public int compare(Map.Entry<OrderAssignment, Double> o1, Map.Entry<OrderAssignment, Double> o2) {
                return (o1.getValue()).compareTo( o2.getValue() );
            }
        });

        Set<Integer> assignedOrders = new HashSet<>();
        Set<Integer> assignedDe = new HashSet<>();

        for (Map.Entry<OrderAssignment, Double> element : list){
            if (!assignedOrders.contains(element.getKey().getOrder().getOrderId()) && !assignedDe.contains(element.getKey().getDeliveryExec().getId())) {
                finalAssignment.put(element.getKey().getOrder(), element.getKey().getDeliveryExec());
                assignedDe.add(element.getKey().getDeliveryExec().getId());
                assignedOrders.add(element.getKey().getOrder().getOrderId());
            }
        }
        return finalAssignment;
    }
}
