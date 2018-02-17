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

    public void updateScores(List<Pair<OrderAssignment, Double>> attributeScore, Double weight) {
        for (Pair<OrderAssignment, Double> assign : attributeScore) {
            if (scores.containsKey(assign.getKey())) {
                scores.put(assign.getKey(), weight * assign.getValue() + scores.get(assign.getKey()));
            } else scores.put(assign.getKey(), weight * assign.getValue());
        }
    }

    public List<Pair<OrderAssignment, Double>> getUpdatedScoresAsList() {
        ArrayList<Pair<OrderAssignment, Double>> allCombinationScores = new ArrayList<>();
        for (Map.Entry<OrderAssignment, Double> entries : scores.entrySet()) {
            allCombinationScores.add(new Pair<>(entries.getKey(), entries.getValue()));
        }
        return allCombinationScores;
    }

    public HashMap<OrderAssignment, Double> getScores() {
        return scores;
    }

    public List<Order> getOrderList() {
        Set<Order> orders = new HashSet<>();
        scores.keySet().forEach(orderAssignment -> orders.add(orderAssignment.getOrder()));
        return new ArrayList<>(orders);
    }

    public List<DeliveryExec> getDeList() {
        Set<DeliveryExec> deliveryExecs = new HashSet<>();
        scores.keySet().forEach(orderAssignment -> deliveryExecs.add(orderAssignment.getDeliveryExec()));
        return new ArrayList<>(deliveryExecs);
    }
}
