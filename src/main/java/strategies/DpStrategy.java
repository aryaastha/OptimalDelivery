package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;
import utils.AssignmentHelper;
import utils.ScoreComputer;

import java.util.*;

/**
 * Created by astha.a on 14/02/18.
 */
public class DpStrategy implements IStrategy {
    @Override
    public List<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) {
        Set<OrderAssignment> finalAssignment = new HashSet<>();

        AssignmentHelper helper = new AssignmentHelper(updatedScores);
        Integer size = helper.getSize();
        List<Order> listOfOrders = helper.getOrders();
        List<DeliveryExec> listOfExecs = helper.getDeliveryExecs();

        Integer N = listOfExecs.size();

        int numberOfPerms = (int) (Math.pow(2D, N));

        double[] dp = new double[numberOfPerms];
        Arrays.fill(dp, Double.MAX_VALUE);

        Map<Integer, Pair<OrderAssignment, Integer>> optimalAssignmentMap = new HashMap<>();

        double[][] cost = helper.getCostArray();

        dp[0] = 0D;

        for (Integer mask = 0; mask < numberOfPerms; mask++) {
            Integer order = countSetBits(mask, N);
            for (Integer exec = 0; exec < N && order < size; exec++) {
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

        Pair<OrderAssignment, Integer> optimalAssignmentMask;

        while (optimalMask > 0) {
            optimalAssignmentMask = optimalAssignmentMap.get(optimalMask);
            finalAssignment.add(optimalAssignmentMask.getKey());
            optimalMask = optimalAssignmentMask.getValue();
        }

        return helper.filterDummies(finalAssignment);
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
        return (mask & (1 << bit)) != 0;
    }

}
