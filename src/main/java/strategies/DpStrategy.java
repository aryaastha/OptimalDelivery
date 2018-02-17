package strategies;

import beans.*;
import javafx.util.Pair;
import utils.ScoreComputer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by astha.a on 14/02/18.
 */
public class DpStrategy implements IStrategy {
    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) {
        HashMap<OrderAssignment, Double> allCombinationScoreList = updatedScores.getScores();

        ArrayList<OrderAssignment> finalAssignment = new ArrayList<>();

        ArrayList<Order> listOfOrders = updatedScores.getOrderList();
        ArrayList<DeliveryExec> listOfExecs = updatedScores.getDeList();

        Integer N = listOfExecs.size();

        int numberOfPerms = (int) (Math.pow(2D, N));

        Double[] dp = new Double[numberOfPerms];

        HashMap<Integer, Pair<OrderAssignment, Integer>> optimalAssignmentMap = new HashMap<>();
        for (Integer i = 0; i < numberOfPerms; i++) {
            dp[i] = Double.MAX_VALUE;
        }
        if (listOfExecs.size() > listOfOrders.size()){
            insertDummyOrders(listOfOrders, listOfExecs.size()-listOfOrders.size());
        }

        if (listOfExecs.size() < listOfOrders.size()){
            insertDummyDe(listOfExecs, listOfOrders.size()-listOfExecs.size());
        }

        Double[][] cost = new Double[listOfOrders.size()][listOfExecs.size()];
        for (int i = 0; i < listOfOrders.size(); i++) {
            for (int j = 0; j < listOfExecs.size(); j++) {
                if (allCombinationScoreList.containsKey(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(j)))) {
                    cost[i][j] = allCombinationScoreList.get(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(j)));
                }else cost[i][j] = 0.0;
            }
        }

        dp[0] = 0D;

        for (Integer mask = 0; mask < numberOfPerms; mask++) {
            Integer order = countSetBits(mask, N);
            for (Integer exec = 0; exec < N && order<listOfOrders.size(); exec++) {
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
        double min = Integer.MAX_VALUE;
        if (listOfExecs.size() == listOfOrders.size()){
            optimalMask = numberOfPerms - 1;
        }else if (listOfExecs.size() > listOfOrders.size()){
            for(int i = 0;i<numberOfPerms;i++){
                Integer integer = countSetBits(i, N);
                if (integer == listOfOrders.size()){
                    if(min>dp[i]){
                        min = dp[i];
                        optimalMask = i;
                    }
                }
            }
        }



        Pair<OrderAssignment, Integer> orderAssignmentIntegerPair;
        while (optimalMask > 0) {
            orderAssignmentIntegerPair = optimalAssignmentMap.get(optimalMask);
            if(!(orderAssignmentIntegerPair.getKey().getOrder() instanceof DummyOrder) && !(orderAssignmentIntegerPair.getKey().getDeliveryExec() instanceof DummyDE)) {
                finalAssignment.add(orderAssignmentIntegerPair.getKey());
            }
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
        return (mask & (1 << bit)) != 0;
    }


    private void insertDummyOrders(ArrayList<Order> listOfOrders, int n){
        for(int i = 0; i < n ; i++){
            listOfOrders.add(new DummyOrder(0, new Restaurant(new Location(0D,0D)),0D));
        }
    }

    private void insertDummyDe(ArrayList<DeliveryExec> listOfOrders, int n){
        for(int i = 0; i < n ; i++){
            listOfOrders.add(new DummyDE(0, new Location(0D,0D),0D));
        }
    }
}
