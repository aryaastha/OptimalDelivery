package attributes;


import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by astha.a on 12/02/18.
 */
public interface IAttribute<T> {
    public ArrayList<Pair<OrderAssignment, Double>> getScore(ArrayList<Order> order, ArrayList<DeliveryExec> de);
    public HashMap<T,Double> getNormalisedScore(ArrayList<T> orders);
    public Double getWeight();
}
