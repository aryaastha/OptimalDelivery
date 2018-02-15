package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import utils.UpdateScores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by astha.a on 14/02/18.
 */
public class DpStrategy implements IStrategy {
    public DpStrategy() {}

    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(UpdateScores updatedScores) {
        HashMap<OrderAssignment, Double> allCombinationScoreList = updatedScores.getUpdatedScores();

        HashMap<Order, DeliveryExec> finalAssignmentTemp = new HashMap<>();
        ArrayList<OrderAssignment> finalAssignment = new ArrayList<>();

        ArrayList<Order> listOfOrders = new ArrayList<>();
        ArrayList<DeliveryExec> listOfExecs = new ArrayList<>();

        for (Map.Entry<OrderAssignment, Double> entries : allCombinationScoreList.entrySet()) {
            Order order = entries.getKey().getOrder();
            DeliveryExec deliveryExec = entries.getKey().getDeliveryExec();

            if (!listOfExecs.contains(deliveryExec))
                listOfExecs.add(deliveryExec);

            if (!listOfOrders.contains(order))
                listOfOrders.add(order);

        }

        Integer N = listOfExecs.size();

        Double numberOfPerms = (Math.pow(2D, N));

        Double[] dp = new Double[numberOfPerms.intValue()];
        for (Long i = 0L; i < numberOfPerms.intValue(); i++) {
            dp[i.intValue()] = Double.MAX_VALUE;
        }

        Double[][] cost =new Double[listOfOrders.size()][listOfExecs.size()];
        for (int i = 0; i < listOfOrders.size(); i++){
            for (int j = 0; j < listOfExecs.size(); j++){
                cost[i][j] = allCombinationScoreList.get(new OrderAssignment(listOfOrders.get(0),listOfExecs.get(j)));
            }
        }

        dp[0] = 0D;

        for (Long mask = 0L; mask < numberOfPerms.intValue(); mask++) {
            Long x = countSetBits(mask, N);
            for(Long j = 0L; j < N; j++) {
                if(!checkIfSet(mask, j)){
                    Long index = mask | (1 << j);
                    dp[index.intValue()] = Math.min(dp[index.intValue()], dp[mask.intValue()]+cost[x.intValue()][j.intValue()]);
                    finalAssignmentTemp.put(listOfOrders.get(x.intValue()),listOfExecs.get(j.intValue()));
                }
            }

        }

        for(Map.Entry<Order, DeliveryExec> entry : finalAssignmentTemp.entrySet()){
            finalAssignment.add(new OrderAssignment(entry.getKey(),entry.getValue()));
        }


        System.out.println("Size of final Assignment : " + finalAssignmentTemp.size());

        System.out.println("Minimum Cost : " + dp[numberOfPerms.intValue()-1]);

        for(int i = 0 ; i < listOfOrders.size(); i++){
            for (int j = 0; j < listOfExecs.size(); j++){
                System.out.print(cost[i][j] + " ");
            }
            System.out.println();
        }
        return finalAssignment;
    }

    private Long countSetBits(Long mask, int N){
        long count = 0;
        for(Long j = 0L; j < N; j++){
            if(checkIfSet(mask, j)){
                count++;
            }
        }
        return count;
    }

    private boolean checkIfSet(long mask, Long bit){
        if((mask&(1<<bit))!=0)
            return true;
        return false;
    }


}
