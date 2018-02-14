package attributes;

import beans.Assignment;
import beans.DeliveryExec;
import beans.Order;

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
    public ArrayList<Assignment> getAttributeWiseScore(ArrayList<Order> orders, ArrayList<DeliveryExec> de) {
        ArrayList<Assignment> allCombinations = new ArrayList<Assignment>();

        HashMap<Order, Double> orderScore = normalizeOrderTimes(orders);

        for (Order order: orders){
            Double value = orderScore.get(order);
            for (DeliveryExec deliveryExec : de){
                allCombinations.add(new Assignment(deliveryExec, order, weight*value));
            }
        }

        return allCombinations;
    }

    public HashMap<Order,Double> normalizeOrderTimes(ArrayList<Order> orders){
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
}
