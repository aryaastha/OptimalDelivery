package attributes;


import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by astha.a on 12/02/18.
 */
public interface IAttribute {
    ArrayList<Pair<OrderAssignment, Double>> getNormalisedScore(ArrayList<Order> order, ArrayList<DeliveryExec> de);
    Double getWeight();
}
