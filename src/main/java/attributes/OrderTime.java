package attributes;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by astha.a on 13/02/18.
 */
public class OrderTime implements IAttribute<Order> {
    private Double weight;

    public OrderTime(Double weight) {
        this.weight = weight;
    }
    public ArrayList<Pair<OrderAssignment, Double>> getScore(ArrayList<Order> orders, ArrayList<DeliveryExec> de) {
        ArrayList<Pair<OrderAssignment, Double>> allCombinations = new ArrayList<Pair<OrderAssignment, Double>>();

        HashMap<Order, Double> orderScore = getNormalisedScore(orders);

        for (Order order: orders){
            Double value = orderScore.get(order);
            for (DeliveryExec deliveryExec : de){
                allCombinations.add(new Pair<>(new OrderAssignment(order, deliveryExec), value));
            }
        }

        return allCombinations;
    }

    public HashMap<Order,Double> getNormalisedScore(ArrayList<Order> orders){
        Double maxIntime = 0D;
        Double minIntime = Double.MAX_VALUE;

        for (Order order : orders){
            if (order.getOrderedTime()<minIntime){
                minIntime = order.getOrderedTime();
            }

            if (order.getOrderedTime()>maxIntime){
                maxIntime = order.getOrderedTime();
            }
        }

        HashMap<Order, Double> valuePerOrder = new HashMap<Order, Double>();
        for (Order order: orders){
            valuePerOrder.put(order, order.getOrderedTime()/(maxIntime-minIntime));
        }

        return valuePerOrder;
    }

    public Double getWeight() {
        return weight;
    }
}
