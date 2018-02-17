package utils;

import beans.*;

import java.util.*;
import java.util.stream.Collectors;

import static beans.DummyDE.getDummyDeliveryExecs;
import static beans.DummyOrder.getDummyOrders;

/**
 * Created by astha.a on 17/02/18.
 */
public class AssignmentHelper {
    private ScoreComputer computer;
    private List<Order> orders;
    private List<DeliveryExec> deliveryExecs;

    public AssignmentHelper(ScoreComputer computer) {
        this.computer = computer;
        this.orders = new ArrayList<>(computer.getOrderList());
        this.deliveryExecs = new ArrayList<>(computer.getDeList());
        balance();
    }

    private void balance(){
        orders.addAll(getDummyOrders(deliveryExecs.size()- orders.size()));
        deliveryExecs.addAll(getDummyDeliveryExecs(orders.size() - deliveryExecs.size()));
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<DeliveryExec> getDeliveryExecs() {
        return deliveryExecs;
    }

    public double[][] getCostArray() {
        HashMap<OrderAssignment, Double> scores = computer.getScores();
        double[][] cost = new double[orders.size()][deliveryExecs.size()];
        for (double[] c : cost)
            Arrays.fill(c,0D);

        for (int i = 0; i < orders.size(); i++) {
            for (int j = 0; j < deliveryExecs.size(); j++) {
                if (scores.containsKey(new OrderAssignment(orders.get(i), deliveryExecs.get(j)))) {
                    cost[i][j] = scores.get(new OrderAssignment(orders.get(i), deliveryExecs.get(j)));
                }
            }
        }
        return cost;
    }

    public List<OrderAssignment> filterDummies(Set<OrderAssignment> optimalAssignments){
        return optimalAssignments.stream().filter(orderAssignment ->
                !(orderAssignment.getOrder() instanceof DummyOrder) &&
                        !(orderAssignment.getDeliveryExec() instanceof DummyDE)).collect(Collectors.toList());

    }
}
