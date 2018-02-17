package beans;

import java.util.Collections;
import java.util.List;

/**
 * Created by astha.a on 17/02/18.
 */
public class DummyDE extends DeliveryExec {
    public DummyDE() {
        super(0, new Location(0D, 0D), 0D);
    }

    public static List<DeliveryExec> getDummyDeliveryExecs(int n) {
        if (n > 0) return Collections.nCopies(n, new DummyDE());
        return Collections.emptyList();
    }
}
