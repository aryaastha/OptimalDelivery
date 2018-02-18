import attributes.IAttribute;
import beans.*;
import com.google.common.collect.ImmutableMap;
import javafx.util.Pair;
import org.junit.BeforeClass;
import strategies.DpStrategy;
import strategies.GreedyStrategy;
import strategies.LpStrategy;
import utils.FileUtils;
import utils.GsonFactory;
import utils.ScoreComputer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astha.a on 17/02/18.
 */
public class StrategyNMTest extends StrategyTest{
    @BeforeClass
    public static void setUp() throws Exception {
        ArrayList<Order> orders = new ArrayList<Order>();


        int t = 0;

        orders.add(new Order(++t, new Restaurant(new Location(2D, 5D)), 9D));
        orders.add(new Order(++t, new Restaurant(new Location(2D, 4D)), 7D));
        orders.add(new Order(++t, new Restaurant(new Location(1D, 4D)), 6D));
        orders.add(new Order(++t, new Restaurant(new Location(4D, 1D)), 8D));


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


        results = ImmutableMap.of(DpStrategy.class,27.378, LpStrategy.class,27.378, GreedyStrategy.class,28.264);


    }

}
