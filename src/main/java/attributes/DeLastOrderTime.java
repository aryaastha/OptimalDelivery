package attributes;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by astha.a on 13/02/18.
 */
public class DeLastOrderTime implements IAttribute {
    private Double weight;

    public DeLastOrderTime(Double weight) {
        this.weight = weight;
    }

    public List<Pair<OrderAssignment, Double>> getNormalisedScore(List<Order> order, List<DeliveryExec> de) {
        List<Pair<OrderAssignment, Double>> allCombinations = new ArrayList<Pair<OrderAssignment, Double>>();

        HashMap<DeliveryExec, Double> deScore = getNormalisedScore(de);

        for (DeliveryExec d : de) {
            Double value = deScore.get(d);
            for (Order o : order) {
                allCombinations.add(new Pair<>(new OrderAssignment(o, d), value));
            }
        }

        return allCombinations;
    }

    private HashMap<DeliveryExec, Double> getNormalisedScore(List<DeliveryExec> deliveryExecs) {
        Double maxLastOrderTime = 0D;
        Double minLastOrderTime = Double.MAX_VALUE;

        for (DeliveryExec de : deliveryExecs) {
            if (de.getLastOrderDeliveryTime() < minLastOrderTime) {
                minLastOrderTime = de.getLastOrderDeliveryTime();
            }

            if (de.getLastOrderDeliveryTime() > maxLastOrderTime) {
                maxLastOrderTime = de.getLastOrderDeliveryTime();
            }
        }

        HashMap<DeliveryExec, Double> valueperDe = new HashMap<DeliveryExec, Double>();
        for (DeliveryExec de : deliveryExecs) {
            valueperDe.put(de, de.getLastOrderDeliveryTime() / (maxLastOrderTime - minLastOrderTime));
        }

        return valueperDe;
    }

    public Double getWeight() {
        return weight;
    }
}
