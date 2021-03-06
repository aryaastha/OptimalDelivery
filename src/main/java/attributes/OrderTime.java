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
public class OrderTime implements IAttribute {
    private Double weight;

    public OrderTime(Double weight) {
        this.weight = weight;
    }

    public List<Pair<OrderAssignment, Double>> getNormalisedScore(List<Order> orders, List<DeliveryExec> de) {
        List<Pair<OrderAssignment, Double>> allCombinations = new ArrayList<Pair<OrderAssignment, Double>>();

        HashMap<Order, Double> orderScore = getNormalisedScore(orders);

        for (Order order : orders) {
            Double value = orderScore.get(order);
            for (DeliveryExec deliveryExec : de) {
                allCombinations.add(new Pair<>(new OrderAssignment(order, deliveryExec), value));
            }
        }

        return allCombinations;
    }

    private HashMap<Order, Double> getNormalisedScore(List<Order> orders) {
        Double maxIntime = 0D;
        Double minIntime = Double.MAX_VALUE;

        for (Order order : orders) {
            if (order.getOrderedTime() < minIntime) {
                minIntime = order.getOrderedTime();
            }

            if (order.getOrderedTime() > maxIntime) {
                maxIntime = order.getOrderedTime();
            }
        }

        HashMap<Order, Double> valuePerOrder = new HashMap<Order, Double>();
        for (Order order : orders) {
            valuePerOrder.put(order, order.getOrderedTime() / (maxIntime - minIntime));
        }

        return valuePerOrder;
    }

    public Double getWeight() {
        return weight;
    }
}
