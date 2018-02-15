package strategies;

import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by astha.a on 14/02/18.
 */
public class GreedyStrategy implements IStrategy {
    public GreedyStrategy() {}

    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(ArrayList<Pair<OrderAssignment, Double>> allCombinationScoreList) {
        ArrayList<OrderAssignment> finalAssignment = new ArrayList<>();

        Collections.sort(allCombinationScoreList, new Comparator<Pair<OrderAssignment, Double>>() {
            @Override
            public int compare(Pair<OrderAssignment, Double> o1, Pair<OrderAssignment, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Set<Integer> assignedOrders = new HashSet<>();
        Set<Integer> assignedDe = new HashSet<>();

        for (Pair<OrderAssignment, Double> element : allCombinationScoreList){
            if (!assignedOrders.contains(element.getKey().getOrder().getOrderId()) && !assignedDe.contains(element.getKey().getDeliveryExec().getId())) {
                finalAssignment.add(element.getKey());
                assignedDe.add(element.getKey().getDeliveryExec().getId());
                assignedOrders.add(element.getKey().getOrder().getOrderId());
            }
        }
        return finalAssignment;
    }
}
