import attributes.IAttribute;
import beans.*;
import com.google.common.collect.ImmutableMap;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategies.DpStrategy;
import strategies.GreedyStrategy;
import strategies.IStrategy;
import strategies.LpStrategy;
import utils.FileUtils;
import utils.GsonFactory;
import utils.ScoreComputer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by astha.a on 15/02/18.
 */
public class StrategyTest {
    static ScoreComputer computer;
    static Map<Class, Double> results;
    private static final double DELTA = 1e-3;

    @BeforeClass
    public static void setUp() throws Exception {
        ArrayList<Order> orders = new ArrayList<Order>();

        int t = 0;

        orders.add(new Order(++t, new Restaurant(new Location(2D, 5D)), 9D));
        orders.add(new Order(++t, new Restaurant(new Location(2D, 4D)), 7D));
        orders.add(new Order(++t, new Restaurant(new Location(1D, 4D)), 9D));


        ArrayList<DeliveryExec> executives = new ArrayList<DeliveryExec>();

        executives.add(new DeliveryExec(++t, new Location(4D, 1D), 2D));
        executives.add(new DeliveryExec(++t, new Location(6D, 8D), 5D));
        executives.add(new DeliveryExec(++t, new Location(9D, 10D), 3D));


        computer = new ScoreComputer();

        String mappingForDp = FileUtils.readFromFile("src/test/resources/attributes.json");
        IAttribute[] array = GsonFactory.getInstance().getGson().fromJson(mappingForDp, IAttribute[].class);
        for (IAttribute attribute : array) {
            List<Pair<OrderAssignment, Double>> attributeScore = attribute.getNormalisedScore(orders, executives);
            computer.updateScores(attributeScore, attribute.getWeight());
        }

        results = ImmutableMap.of(DpStrategy.class,46.611,LpStrategy.class,46.611,GreedyStrategy.class,46.656);

    }

    @Test
    public void testDp() throws Exception {
        IStrategy strategy = new DpStrategy();
        List<OrderAssignment> finalAssignment = strategy.getFinalAssignment(computer);
        System.out.println(calculateCosts(finalAssignment));
        Assert.assertEquals(results.get(strategy.getClass()),calculateCosts(finalAssignment),DELTA);
    }

    @Test
    public void testLp() throws Exception {
        IStrategy strategy = new LpStrategy();
        List<OrderAssignment> finalAssignment = strategy.getFinalAssignment(computer);
        System.out.println(calculateCosts(finalAssignment));
        Assert.assertEquals(results.get(strategy.getClass()),calculateCosts(finalAssignment),DELTA);
    }

    @Test
    public void testGreedy() throws Exception {
        IStrategy strategy = new GreedyStrategy();
        List<OrderAssignment> finalAssignment = strategy.getFinalAssignment(computer);
        System.out.println(calculateCosts(finalAssignment));
        Assert.assertEquals(results.get(strategy.getClass()),calculateCosts(finalAssignment),DELTA);
    }

    private Double calculateCosts(List<OrderAssignment> finalAssignment) {
        double cost = 0D;
        HashMap<OrderAssignment, Double> scores = computer.getScores();
        for (OrderAssignment assign : finalAssignment) {
            cost += scores.get(assign);
        }
        return cost;
    }
}
