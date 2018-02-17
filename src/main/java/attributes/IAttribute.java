package attributes;


import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.List;

/**
 * Created by astha.a on 12/02/18.
 */
public interface IAttribute {
    List<Pair<OrderAssignment, Double>> getNormalisedScore(List<Order> order, List<DeliveryExec> de);
    Double getWeight();
}
