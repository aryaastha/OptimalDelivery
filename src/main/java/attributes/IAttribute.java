package attributes;


import beans.Assignment;
import beans.DeliveryExec;
import beans.Order;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by astha.a on 12/02/18.
 */
public interface IAttribute<T> {
    public ArrayList<Assignment> getScore(ArrayList<Order> order, ArrayList<DeliveryExec> de);
    public HashMap<T,Double> getNormalisedScore(ArrayList<T> orders);
}
