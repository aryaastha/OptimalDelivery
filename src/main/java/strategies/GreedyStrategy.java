package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;
import utils.ScoreComputer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by astha.a on 14/02/18.
 */
public class GreedyStrategy implements IStrategy {

    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) {
        ArrayList<Pair<OrderAssignment, Double>> allCombinationScoreList = updatedScores.getUpdatedScoresAsList();
        ArrayList<OrderAssignment> finalAssignment = new ArrayList<>();

        Collections.sort(allCombinationScoreList, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        Set<Order> assignedOrders = new HashSet<>();
        Set<DeliveryExec> assignedDe = new HashSet<>();

        for (Pair<OrderAssignment, Double> element : allCombinationScoreList) {
            if (!assignedOrders.contains(element.getKey().getOrder()) && !assignedDe.contains(element.getKey().getDeliveryExec())) {
                finalAssignment.add(element.getKey());
                assignedDe.add(element.getKey().getDeliveryExec());
                assignedOrders.add(element.getKey().getOrder());
            }
        }
        return finalAssignment;
    }
}
