package beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astha.a on 17/02/18.
 */
public class DummyDE extends DeliveryExec {
    public static List<DeliveryExec> getDummyDeliveryExecs(int n) {
        List<DeliveryExec> dummyList = new ArrayList<>(n);
        for (int i = 0; i < n; i++){
            dummyList.add(new DummyDE());
        }
        return dummyList;
    }
}
