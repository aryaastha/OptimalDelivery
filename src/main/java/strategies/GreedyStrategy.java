package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;
import utils.UpdateScores;

import java.util.*;

/**
 * Created by astha.a on 14/02/18.
 */
public class GreedyStrategy implements IStrategy {
    public GreedyStrategy() {}

    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(UpdateScores updatedScores) {
        ArrayList<Pair<OrderAssignment, Double>> allCombinationScoreList = updatedScores.getUpdatedScoresAsList();
        ArrayList<OrderAssignment> finalAssignment = new ArrayList<>();

        Collections.sort(allCombinationScoreList, new Comparator<Pair<OrderAssignment, Double>>() {
            @Override
            public int compare(Pair<OrderAssignment, Double> o1, Pair<OrderAssignment, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        Set<Order> assignedOrders = new HashSet<>();
        Set<DeliveryExec> assignedDe = new HashSet<>();

        for (Pair<OrderAssignment, Double> element : allCombinationScoreList){
            if (!assignedOrders.contains(element.getKey().getOrder()) && !assignedDe.contains(element.getKey().getDeliveryExec())) {
                finalAssignment.add(element.getKey());
                assignedDe.add(element.getKey().getDeliveryExec());
                assignedOrders.add(element.getKey().getOrder());
            }
        }
        return finalAssignment;
    }
}
