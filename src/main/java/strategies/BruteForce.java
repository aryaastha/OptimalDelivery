package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by astha.a on 14/02/18.
 */
public class BruteForce {
    LinkedHashMap<OrderAssignment, Double> allCombinationScoreList = new LinkedHashMap<>();
    public HashMap<Order, DeliveryExec> getFinalAssignment(LinkedHashMap<OrderAssignment, Double> allCombinationScoreList){
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<DeliveryExec> executives = new ArrayList<>();
        return null;

    }
}
