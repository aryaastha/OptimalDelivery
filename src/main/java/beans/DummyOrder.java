package beans;

import java.util.Collections;
import java.util.List;

/**
 * Created by astha.a on 17/02/18.
 */
public class DummyOrder extends Order {
    public DummyOrder() {
        super(0, new Restaurant(new Location(0D, 0D)), 0D);
    }

    public static List<Order> getDummyOrders(int n) {
        if (n > 0) return Collections.nCopies(n, new DummyOrder());
        return Collections.emptyList();
    }
}
