package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;
import utils.ScoreComputer;

import java.util.*;

/**
 * Created by astha.a on 14/02/18.
 */
public class GreedyStrategy implements IStrategy {

    public class AssignmentComparator implements Comparator<Pair<OrderAssignment, Double>>{
        @Override
        public int compare(Pair<OrderAssignment, Double> o1, Pair<OrderAssignment, Double> o2) {
            int orderIdCompare = o1.getKey().getOrder().getOrderId().compareTo(o2.getKey().getOrder().getOrderId());
            int deIdCompare = o1.getKey().getDeliveryExec().getId().compareTo(o2.getKey().getDeliveryExec().getId());
            int costCompare = o1.getValue().compareTo(o2.getValue());
            if (costCompare != 0)
                return costCompare;
            else if (orderIdCompare != 0){
                return orderIdCompare;
            }else if (deIdCompare != 0){
                return deIdCompare;
            }
            return 0;
        }
    }

    @Override
    public List<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) {
        List<Pair<OrderAssignment, Double>> allCombinationScoreList = updatedScores.getUpdatedScoresAsList();
        ArrayList<OrderAssignment> finalAssignment = new ArrayList<>();

        Collections.sort(allCombinationScoreList,new AssignmentComparator());

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
