package attributes;

import utils.LocationUtils;
import beans.Assignment;
import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by astha.a on 13/02/18.
 */
public class MinimumDistance implements IAttribute<OrderAssignment> {


    public ArrayList<Assignment> getAttributeWiseScore(ArrayList<Order> order, ArrayList<DeliveryExec> de) {
        ArrayList<OrderAssignment> everyPossibleCombination = new ArrayList<OrderAssignment>();

        for (Order order1 : order){
            for(DeliveryExec d : de){
                everyPossibleCombination.add(new OrderAssignment(order1, d));
            }
        }

        HashMap<OrderAssignment, Double> assignmentScore = normalizeOrderTimes(everyPossibleCombination);
        return null;
    }

    public HashMap<OrderAssignment, Double> normalizeOrderTimes(ArrayList<OrderAssignment> assignments) {

        Double maxDistance = 0D;
        Double minDistance = Double.MAX_VALUE;


        HashMap<OrderAssignment, Double> valuePerCombo = new HashMap<OrderAssignment, Double>();
        for (OrderAssignment assignment : assignments){
            Double distance = LocationUtils.distance(assignment.getDeliveryExec().getCurrentLocation(), assignment.getOrder().getRestaurant().getRestaurantLocation());
            if (distance>maxDistance){
                maxDistance = distance;
            }
            
            if (distance<minDistance){
                minDistance = distance;
            }

            valuePerCombo.put(assignment, distance);
        }


        for (OrderAssignment assignment : assignments){
            Double comboDistance = valuePerCombo.get(assignment);
            valuePerCombo.put(assignment, comboDistance /(maxDistance-minDistance));
        }

        return valuePerCombo;
    }
}
