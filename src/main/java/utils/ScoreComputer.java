package utils;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.*;

/**
 * Created by astha.a on 15/02/18.
 */
public class ScoreComputer {
    private HashMap<OrderAssignment, Double> scores;

    public ScoreComputer() {
        scores = new HashMap<>();
    }

    public void updateScores(ArrayList<Pair<OrderAssignment, Double>> attributeScore, Double weight) {
        for (Pair<OrderAssignment, Double> assign : attributeScore) {
            if (scores.containsKey(assign.getKey())) {
                scores.put(assign.getKey(), weight * assign.getValue() + scores.get(assign.getKey()));
            } else scores.put(assign.getKey(), weight * assign.getValue());
        }
    }

    public ArrayList<Pair<OrderAssignment, Double>> getUpdatedScoresAsList() {
        ArrayList<Pair<OrderAssignment, Double>> allCombinationScores = new ArrayList<>();
        for (Map.Entry<OrderAssignment, Double> entries : scores.entrySet()) {
            allCombinationScores.add(new Pair<>(entries.getKey(), entries.getValue()));
        }
        return allCombinationScores;
    }

    public HashMap<OrderAssignment, Double> getScores() {
        return scores;
    }

    public ArrayList<Order> getOrderList(){
        Set<Order> orders = new HashSet<>();
        ArrayList<Order> listOfOrders = new ArrayList<>();
        Set<OrderAssignment> orderAssignments = scores.keySet();
        orderAssignments.forEach(orderAssignment -> orders.add(orderAssignment.getOrder()));
        listOfOrders.addAll(orders);
        return listOfOrders;
    }

    public ArrayList<DeliveryExec> getDeList(){
        Set<DeliveryExec> deliveryExecs = new HashSet<>();
        ArrayList<DeliveryExec> listOfDe = new ArrayList<>();
        Set<OrderAssignment> orderAssignments = scores.keySet();
        orderAssignments.forEach(orderAssignment -> deliveryExecs.add(orderAssignment.getDeliveryExec()));
        listOfDe.addAll(deliveryExecs);
        return listOfDe;
    }
}
