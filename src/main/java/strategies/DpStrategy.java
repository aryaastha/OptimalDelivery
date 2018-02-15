package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;
import utils.UpdateScores;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by astha.a on 14/02/18.
 */
public class DpStrategy implements IStrategy {
    public DpStrategy() {
    }

    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(UpdateScores updatedScores) {
        HashMap<OrderAssignment, Double> allCombinationScoreList = updatedScores.getUpdatedScores();

        ArrayList<OrderAssignment> finalAssignment = new ArrayList<>();

        ArrayList<Order> listOfOrders = new ArrayList<>();
        ArrayList<DeliveryExec> listOfExecs = new ArrayList<>();

        for (OrderAssignment entries : allCombinationScoreList.keySet()) {
            Order order = entries.getOrder();
            DeliveryExec deliveryExec = entries.getDeliveryExec();

            if (!listOfExecs.contains(deliveryExec))
                listOfExecs.add(deliveryExec);

            if (!listOfOrders.contains(order))
                listOfOrders.add(order);

        }

        Integer N = listOfExecs.size();

        int numberOfPerms = (int) (Math.pow(2D, N));

        Double[] dp = new Double[numberOfPerms];

        HashMap<Integer, Pair<OrderAssignment, Integer>> optimalAssignmentMap = new HashMap<>();
        for (Integer i = 0; i < numberOfPerms; i++) {
            dp[i] = Double.MAX_VALUE;
        }

        Double[][] cost = new Double[listOfOrders.size()][listOfExecs.size()];
        for (int i = 0; i < listOfOrders.size(); i++) {
            for (int j = 0; j < listOfExecs.size(); j++) {
                cost[i][j] = allCombinationScoreList.get(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(j)));
            }
        }

        dp[0] = 0D;

        for (Integer mask = 0; mask < numberOfPerms; mask++) {
            Integer order = countSetBits(mask, N);
            for (Integer exec = 0; exec < N; exec++) {
                if (!checkIfSet(mask, exec)) {
                    Integer index = mask | (1 << exec);
                    if (dp[index] > (dp[mask] + cost[order][exec])) {
                        optimalAssignmentMap.put(index, new Pair<>(new OrderAssignment(listOfOrders.get(order), listOfExecs.get(exec)), mask));
                        dp[index] = dp[mask] + cost[order][exec];
                    }
                }
            }
        }


        int optimalMask = numberOfPerms - 1;
        Pair<OrderAssignment, Integer> orderAssignmentIntegerPair;
        while (optimalMask > 0) {
            orderAssignmentIntegerPair = optimalAssignmentMap.get(optimalMask);
            finalAssignment.add(orderAssignmentIntegerPair.getKey());
            optimalMask = orderAssignmentIntegerPair.getValue();
        }


        return finalAssignment;
    }

    private Integer countSetBits(Integer mask, int N) {
        Integer count = 0;
        for (Integer j = 0; j < N; j++) {
            if (checkIfSet(mask, j)) {
                count++;
            }
        }
        return count;
    }

    private boolean checkIfSet(Integer mask, Integer bit) {
        if ((mask & (1 << bit)) != 0)
            return true;
        return false;
    }


}
