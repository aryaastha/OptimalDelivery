package attributes;

import beans.Assignment;
import beans.DeliveryExec;
import beans.Order;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by astha.a on 13/02/18.
 */
public class DeLastOrderTime implements IAttribute<DeliveryExec> {
    private Double weight;
    public DeLastOrderTime(Double weight) {
        this.weight = weight;
    }

    public ArrayList<Assignment> getAttributeWiseScore(ArrayList<Order> order, ArrayList<DeliveryExec> de) {
        ArrayList<Assignment> allCombinations = new ArrayList<Assignment>();

        HashMap<DeliveryExec, Double> deScore = normalizeOrderTimes(de);

        for (DeliveryExec d: de){
            Double value = deScore.get(d);
            for (Order o : order){
                allCombinations.add(new Assignment(d, o, weight * value));
            }
        }

        return allCombinations;
    }

    public HashMap<DeliveryExec, Double> normalizeOrderTimes(ArrayList<DeliveryExec> deliveryExecs) {
        Double maxLastOrderTime = 0D;
        Double minLastOrderTime = Double.MAX_VALUE;

        for (DeliveryExec de : deliveryExecs){
            if (de.getLastOrderDeliveryTime()<minLastOrderTime){
                minLastOrderTime = de.getLastOrderDeliveryTime();
            }

            if (de.getLastOrderDeliveryTime()>maxLastOrderTime){
                maxLastOrderTime = de.getLastOrderDeliveryTime();
            }
        }

        HashMap<DeliveryExec, Double> valueperDe = new HashMap<DeliveryExec, Double>();
        for (DeliveryExec de: deliveryExecs){
            valueperDe.put(de, de.getLastOrderDeliveryTime()/(maxLastOrderTime-minLastOrderTime));
        }

        return valueperDe;
    }
}
