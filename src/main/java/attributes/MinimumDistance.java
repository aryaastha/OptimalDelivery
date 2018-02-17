package attributes;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;
import utils.LocationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by astha.a on 13/02/18.
 */
public class MinimumDistance implements IAttribute {
    private Double weight;

    public MinimumDistance(Double weight) {
        this.weight = weight;
    }


    public List<Pair<OrderAssignment, Double>> getNormalisedScore(List<Order> order, List<DeliveryExec> de) {
        List<OrderAssignment> everyPossibleCombination = new ArrayList<OrderAssignment>();
        List<Pair<OrderAssignment, Double>> allCombinations = new ArrayList<Pair<OrderAssignment, Double>>();
        for (Order order1 : order) {
            for (DeliveryExec d : de) {
                everyPossibleCombination.add(new OrderAssignment(order1, d));
            }
        }

        HashMap<OrderAssignment, Double> assignmentScore = getNormalisedScore(everyPossibleCombination);

        for (OrderAssignment assign : everyPossibleCombination) {
            allCombinations.add(new Pair<>(new OrderAssignment(assign.getOrder(), assign.getDeliveryExec()), assignmentScore.get(assign)));
        }

        return allCombinations;
    }

    private HashMap<OrderAssignment, Double> getNormalisedScore(List<OrderAssignment> assignments) {

        Double maxDistance = 0D;
        Double minDistance = Double.MAX_VALUE;


        HashMap<OrderAssignment, Double> valuePerCombo = new HashMap<OrderAssignment, Double>();
        for (OrderAssignment assignment : assignments) {
            Double distance = LocationUtils.distance(assignment.getDeliveryExec().getCurrentLocation(), assignment.getOrder().getRestaurant().getRestaurantLocation());
            if (distance > maxDistance) {
                maxDistance = distance;
            }

            if (distance < minDistance) {
                minDistance = distance;
            }

            valuePerCombo.put(assignment, distance);
        }


        for (OrderAssignment assignment : assignments) {
            Double comboDistance = valuePerCombo.get(assignment);
            valuePerCombo.put(assignment, comboDistance / (maxDistance - minDistance));
        }

        return valuePerCombo;
    }

    public Double getWeight() {
        return weight;
    }
}
