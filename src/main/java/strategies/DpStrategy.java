package strategies;

import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by astha.a on 14/02/18.
 */
public class DpStrategy implements IStrategy {
    public DpStrategy() {}

    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(ArrayList<Pair<OrderAssignment, Double>> allCombinationScoreList) {

//        ArrayList<Order> listOfOrders = new ArrayList<>();
//        ArrayList<DeliveryExec> listOfExecs = new ArrayList<>();
//
//        for( Map.Entry<OrderAssignment, Double> entries : allCombinationScoreList.entrySet()){
//            Order order = entries.getKey().getOrder();
//            DeliveryExec deliveryExec = entries.getKey().getDeliveryExec();
//
//            if (!listOfExecs.contains(deliveryExec))
//                listOfExecs.add(deliveryExec);
//
//            if (!listOfOrders.contains(order))
//                listOfOrders.add(order);
//
//        }
//
//        System.out.println("length of unique order list : " + listOfOrders.size());
//        System.out.println("length of unique executives list : " + listOfExecs.size());
//        System.out.println("length of allCombinationsScoreList : " + allCombinationScoreList.size());
//
//        Integer N = listOfExecs.size();
//
//        Double numberOfPerms = (Math.pow(2D, N));
//
//        ArrayList<Double> dp = new ArrayList<>(numberOfPerms.intValue());
//        for(Long i = 0L; i< numberOfPerms.intValue(); i++) {
//            dp.set(i.intValue(), Double.MAX_VALUE);
//        }
//
//        dp.set(0, 0D);
//
////        for(long mask = 0; mask < numberOfPerms.intValue(); mask++){
////            long x = countSetBits(mask);
////            for j = 0 to N
////                if jth bit is not set in i
////                    dp[mask|(1<<j)] = min(dp[mask|(1<<j)], dp[mask]+cost[x][j])
////
////        return dp[power(2,N)-1]
//
//        System.out.println(allCombinationScoreList.get(new OrderAssignment(listOfOrders.get(9),listOfExecs.get(9))));
//
        return null;
    }

}
