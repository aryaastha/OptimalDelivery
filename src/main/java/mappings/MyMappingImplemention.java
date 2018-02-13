package mappings;

import attributes.DeLastOrderTime;
import attributes.MinimumDistance;
import attributes.OrderTime;
import beans.Assignment;
import beans.DeliveryExec;
import beans.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by astha.a on 13/02/18.
 */
public class MyMappingImplemention implements Mapping{
    public HashMap<Order, DeliveryExec> getMapping(ArrayList<Order> orders, ArrayList<DeliveryExec> deliveryExec) {
        HashMap<Order, DeliveryExec> finalAssignment = new HashMap<>();

        final double wOrderTime = 1.0;
        double wMinimumDistance = 1.0;
        double wDeLastOrderTime = 1.0;

        OrderTime orderTime = new OrderTime();
        MinimumDistance minimumDistance = new MinimumDistance();
        DeLastOrderTime deLastOrderTime = new DeLastOrderTime();


        ArrayList<Assignment> sOrderTime = orderTime.getAttributeWiseScore(orders, deliveryExec);
        ArrayList<Assignment> sMinimumDistance = minimumDistance.getAttributeWiseScore(orders, deliveryExec);
        ArrayList<Assignment> sDeLastOrderTime = deLastOrderTime.getAttributeWiseScore(orders, deliveryExec);

        sOrderTime.forEach(assignment -> assignment.setCost(wOrderTime*assignment.getCost()));
        sMinimumDistance.forEach(assignment -> assignment.setCost(wMinimumDistance*assignment.getCost()));
        sDeLastOrderTime.forEach(assignment -> assignment.setCost(wDeLastOrderTime*assignment.getCost()));

        boolean b = sOrderTime.addAll(sMinimumDistance);
        boolean b1 = false;
        if (b){
            b1 = sOrderTime.addAll(sDeLastOrderTime);
        }

        if (b1) {
            Collections.sort(sOrderTime, Comparator.comparingDouble(Assignment::getCost));

            for (Assignment ass : sOrderTime) {
                if (!finalAssignment.containsKey(ass.getOrder())){
                    finalAssignment.put(ass.getOrder(), ass.getDeliveryExec());
                }
            }
        }
        return finalAssignment;
    }
}
