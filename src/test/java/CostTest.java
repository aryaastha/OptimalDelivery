import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import com.google.common.collect.ImmutableMap;
import javafx.util.Pair;
import org.junit.BeforeClass;
import strategies.DpStrategy;
import strategies.GreedyStrategy;
import strategies.LpStrategy;
import utils.ListGenerator;
import utils.ScoreComputer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astha.a on 16/02/18.
 */
public class CostTest extends StrategyTest {
    @BeforeClass
    public static void setUp() throws Exception {
        List<Order> orders = ListGenerator.getList(Order.class, 4);
        List<DeliveryExec> execs = ListGenerator.getList(DeliveryExec.class, 4);

        double[][] cost = {
                {11.508636,11.508636, 11.0, 11.5105},
                {10.462922, 10.462922, 10.0, 10.476133},
                {3.0, 3.0, 3.0, 3.0},
                {6.0, 6.0, 6.16246, 6.459075}
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
        results = ImmutableMap.of(DpStrategy.class,30.462, LpStrategy.class,30.462, GreedyStrategy.class,30.5105);
    }
}
