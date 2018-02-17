import beans.*;
import javafx.util.Pair;
import sun.java2d.xr.MutableInteger;

import java.util.ArrayList;
import java.util.HashSet;

import static strategies.LpStrategy.*;

/**
 * Created by astha.a on 16/02/18.
 */
public class TestClass {
    public static void main(String[] args) {
        ArrayList<Order> listOfOrders = new ArrayList<Order>();


        int t = 0;

        listOfOrders.add(new Order(++t,new Restaurant(new Location(2D,5D)),9D));
        listOfOrders.add(new Order(++t,new Restaurant(new Location(2D,4D)),7D));
        listOfOrders.add(new Order(++t,new Restaurant(new Location(1D,4D)),9D));
        listOfOrders.add(new Order(++t,new Restaurant(new Location(1D,4D)),9D));

        ArrayList<DeliveryExec> listOfExecs = new ArrayList<DeliveryExec>();

        listOfExecs.add(new DeliveryExec(++t,new Location(4D,1D),2D));
        listOfExecs.add(new DeliveryExec(++t,new Location(6D,8D),5D));
        listOfExecs.add(new DeliveryExec(++t,new Location(9D,10D),3D));
        listOfExecs.add(new DeliveryExec(++t,new Location(9D,10D),3D));

        Double[][] cost = new Double[listOfOrders.size()][listOfExecs.size()];

        cost[0][0] = 5.0;
        cost[0][1] = 3.0;
        cost[0][2] = 2.0;
        cost[0][3] = 8.0;

        cost[1][0] = 7.0;
        cost[1][1] = 9.0;
        cost[1][2] = 2.0;
        cost[1][3] = 6.0;

        cost[2][0] = 6.0;
        cost[2][1] = 4.0;
        cost[2][2] = 5.0;
        cost[2][3] = 7.0;

        cost[3][0] = 5.0;
        cost[3][1] = 7.0;
        cost[3][2] = 7.0;
        cost[3][3] = 8.0;


//        cost[0][0] = 2.0;
//        cost[0][1] = 0.0;
//        cost[0][2] = 0.0;
//        cost[0][3] = 2.0;
//
//        cost[1][0] = 0.0;
//        cost[1][1] = 6.0;
//        cost[1][2] = 4.0;
//        cost[1][3] = 0.0;
//
//        cost[2][0] = 0.0;
//        cost[2][1] = 2.0;
//        cost[2][2] = 0.0;
//        cost[2][3] = 2.0;
//
//        cost[3][0] = 0.0;
//        cost[3][1] = 2.0;
//        cost[3][2] = 3.0;
//        cost[3][3] = 0.0;


        simplifyRows(cost, listOfOrders.size(), listOfExecs.size());

        simplifyColumns(cost, listOfOrders.size(), listOfExecs.size());


        ArrayList<Pair<Integer, Integer>> optimalPairs = new ArrayList<>();
        HashSet<OrderAssignment> optimalAssignments = new HashSet<>();

        while (optimalAssignments.size() != listOfOrders.size()) {
            optimalPairs.clear();
            optimalAssignments.clear();;
            int[][] isTraversed = new int[listOfOrders.size()][listOfExecs.size()];

            for (int i = 0; i < listOfOrders.size(); i++) {
                for (int j = 0; j < listOfExecs.size(); j++) {
                    isTraversed[i][j] = 0;
                }
            }

            int countOfNumberOfZeroes = 1;

            while (countOfNumberOfZeroes == 1) {


                for (int i = 0; i < listOfOrders.size(); i++) {
                    MutableInteger columnId = new MutableInteger(0);
                    if (countZerosInRow(cost, listOfExecs.size(), i, columnId, isTraversed) == 1) {
                        optimalPairs.add(new Pair<>(i, columnId.getValue()));
                        optimalAssignments.add(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(columnId.getValue())));
                        for (int j = 0; j < listOfOrders.size(); j++) {
                            isTraversed[j][columnId.getValue()]++;
                        }
                    }
                }

                for (int i = 0; i < listOfExecs.size(); i++) {
                    MutableInteger rowId = new MutableInteger(0);
                    if (countZerosInColumn(cost, listOfOrders.size(), i, rowId, isTraversed) == 1) {
                        optimalPairs.add(new Pair<>(rowId.getValue(), i));
                        optimalAssignments.add(new OrderAssignment(listOfOrders.get(rowId.getValue()), listOfExecs.get(i)));
                        for (int j = 0; j < listOfExecs.size(); j++) {
                            isTraversed[rowId.getValue()][j]++;
                        }
                    }

                }

                countOfNumberOfZeroes = 0;

                for (int i = 0; i < listOfOrders.size(); i++) {
                    countOfNumberOfZeroes += countZerosInRow(cost, listOfOrders.size(), i, new MutableInteger(0), isTraversed);
                }
            }



            if (countOfNumberOfZeroes > 1) {
                for (int i = 0; i < listOfOrders.size(); i++) {
                    for (int j = 0; j < listOfExecs.size(); j++) {
                        if (cost[i][j] == 0 && isTraversed[i][j] == 0) {
                            optimalAssignments.add(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(j)));
                            for (int o = 0; o < listOfOrders.size(); o++) {
                                isTraversed[o][j]++;
                            }
                            int k = j+1;
                            int l = i+1;
                            while (k < listOfExecs.size() && l < listOfOrders.size()) {
                                if (cost[l][k] == 0 && isTraversed[l][k] == 0) {
                                    optimalAssignments.add(new OrderAssignment(listOfOrders.get(l), listOfExecs.get(k)));
                                    for (int o = 0; o < listOfOrders.size(); o++) {
                                        isTraversed[o][k]++;
                                    }
                                }
                                k++;
                                l++;
                            }
                            break;
                        }
                    }
                }
            }


            double min = Integer.MAX_VALUE;
            for (int i = 0; i < listOfOrders.size(); i++) {
                for (int j = 0; j < listOfExecs.size(); j++) {
                    if (isTraversed[i][j] == 0 && min > cost[i][j]) {
                        min = cost[i][j];
                    }
                }
            }


            for (int i = 0; i < listOfOrders.size(); i++) {
                for (int j = 0; j < listOfExecs.size(); j++) {
                    if (isTraversed[i][j] > 1) {
                        cost[i][j] += min;
                    } else if (isTraversed[i][j] == 0) {
                        cost[i][j] -= min;
                    }
                }
            }
        }

        optimalAssignments.forEach(System.out::println);
    }
}
