import beans.*;
import javafx.util.Pair;
import strategies.GreedyStrategy;
import strategies.IStrategy;
import utils.ScoreComputer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astha.a on 16/02/18.
 */
public class TestClass {
    public static void main(String[] args) {
        List<Order> listOfOrders = new ArrayList<Order>();


        int t = 0;

        listOfOrders.add(new Order(++t,new Restaurant(new Location(3D,50D)),93D));
        listOfOrders.add(new Order(++t,new Restaurant(new Location(10D,49D)),73D));
        listOfOrders.add(new Order(++t,new Restaurant(new Location(19D,41D)),91D));
        listOfOrders.add(new Order(++t,new Restaurant(new Location(19D,41D)),91D));

        List<DeliveryExec> listOfExecs = new ArrayList<DeliveryExec>();

        listOfExecs.add(new DeliveryExec(++t,new Location(24D,21D),72D));
        listOfExecs.add(new DeliveryExec(++t,new Location(26D,28D),55D));
        listOfExecs.add(new DeliveryExec(++t,new Location(29D,20D),23D));
        listOfExecs.add(new DeliveryExec(++t,new Location(24D,28D),71D));

        double[][] cost = new double[4][listOfExecs.size()];

        cost[0][0] = 2.0;
        cost[0][1] = 0.0;
        cost[0][2] = 0.0;
        cost[0][3] = 2.0;

        cost[1][0] = 0.0;
        cost[1][1] = 6.0;
        cost[1][2] = 4.0;
        cost[1][3] = 0.0;

        cost[2][0] = 0.0;
        cost[2][1] = 2.0;
        cost[2][2] = 0.0;
        cost[2][3] = 2.0;

        cost[3][0] = 0.0;
        cost[3][1] = 2.0;
        cost[3][2] = 3.0;
        cost[3][3] = 0.0;

        ScoreComputer computer = new ScoreComputer();

        for (int i = 0; i < listOfOrders.size(); i++){
            for (int j = 0 ; j < listOfExecs.size(); j++){
                Pair<OrderAssignment, Double> orderAssignmentDoublePair = new Pair<>(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(j)), cost[i][j]);
                List<Pair<OrderAssignment, Double>> objects = new ArrayList<>();
                objects.add(orderAssignmentDoublePair);
                computer.updateScores(objects, 1D);
            }
        }

        IStrategy strategy = new GreedyStrategy();
        List<OrderAssignment> finalAssignment = strategy.getFinalAssignment(computer);

        finalAssignment.forEach(System.out::println);
    }
}
