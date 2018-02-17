package beans;

/**
 * Created by astha.a on 17/02/18.
 */
public class DummyOrder extends Order {
    public DummyOrder(Integer orderId, Restaurant res, Double orderedTime) {
        super(orderId, res, orderedTime);
    }
}
