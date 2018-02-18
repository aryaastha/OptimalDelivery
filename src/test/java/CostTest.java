import beans.*;
import com.google.common.collect.ImmutableMap;
import javafx.util.Pair;
import org.junit.BeforeClass;
import strategies.DpStrategy;
import strategies.GreedyStrategy;
import strategies.LpStrategy;
import utils.ScoreComputer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astha.a on 16/02/18.
 */
public class CostTest extends StrategyTest {
    @BeforeClass
    public static void setUp() {
        List<Order> orders = DummyOrder.getDummyOrders(4);
        List<DeliveryExec> execs = DummyDE.getDummyDeliveryExecs(4);

        double[][] cost = {
                {0.508636, 0.508636, 0.0, 0.5105},
                {0.462922, 0.462922, 0.0, 0.476133},
                {0.0, 0.0, 0.0, 0.0},
                {0.0, 0.0, 0.16246, 0.459075}
        };

        computer = new ScoreComputer();

        for (int i = 0; i < orders.size(); i++) {
            for (int j = 0; j < execs.size(); j++) {
                Pair<OrderAssignment, Double> orderAssignmentDoublePair = new Pair<>(new OrderAssignment(orders.get(i), execs.get(j)), cost[i][j]);
                List<Pair<OrderAssignment, Double>> objects = new ArrayList<>();
                objects.add(orderAssignmentDoublePair);
                computer.updateScores(objects, 1D);
            }
        }
        results = ImmutableMap.of(DpStrategy.class,0.0, LpStrategy.class,0.0, GreedyStrategy.class,3.551);
    }
}
