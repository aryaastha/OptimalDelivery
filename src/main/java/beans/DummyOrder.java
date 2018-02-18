package beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astha.a on 17/02/18.
 */
public class DummyOrder extends Order {
    public static List<Order> getDummyOrders(int n) {
        List<Order> dummyList = new ArrayList<>(n);
        for (int i = 0; i < n; i++){
            dummyList.add(new Order());
        }
        return dummyList;
    }
}
