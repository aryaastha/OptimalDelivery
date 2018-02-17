package strategies;

import beans.*;
import sun.java2d.xr.MutableInteger;
import utils.ScoreComputer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by astha.a on 16/02/18.
 */
public class LpStrategy implements IStrategy {


    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) {
        HashMap<OrderAssignment, Double> allCombinationScoreList = updatedScores.getScores();

        ArrayList<OrderAssignment> finalAssignment = new ArrayList<>();

        ArrayList<Order> listOfOrders = updatedScores.getOrderList();
        ArrayList<DeliveryExec> listOfExecs = updatedScores.getDeList();

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


        simplifyRows(cost, listOfOrders.size(), listOfExecs.size());

        simplifyColumns(cost, listOfOrders.size(), listOfExecs.size());

        HashSet<OrderAssignment> optimalAssignments = new HashSet<>();

        while (optimalAssignments.size() != listOfOrders.size()) {
            optimalAssignments.clear();
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
                        optimalAssignments.add(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(columnId.getValue())));
                        for (int j = 0; j < listOfOrders.size(); j++) {
                            isTraversed[j][columnId.getValue()]++;
                        }
                    }
                }

                for (int i = 0; i < listOfExecs.size(); i++) {
                    MutableInteger rowId = new MutableInteger(0);
                    if (countZerosInColumn(cost, listOfOrders.size(), i, rowId, isTraversed) == 1) {
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


        optimalAssignments.forEach(orderAssignment -> {
            if (!(orderAssignment.getOrder() instanceof  DummyOrder) && !(orderAssignment.getDeliveryExec() instanceof  DummyDE)){
                finalAssignment.add(orderAssignment);
            }
        });

        return finalAssignment;
    }

    public static void simplifyRows(Double[][] cost, int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            double min = Double.MAX_VALUE;
            for (int j = 0; j < columns; j++) {
                if (min > cost[i][j])
                    min = cost[i][j];
            }

            for (int j = 0; j < columns; j++) {
                cost[i][j] = cost[i][j] - min;
            }
        }
    }

    public static void simplifyColumns(Double[][] cost, int rows, int columns) {
        for (int i = 0; i < columns; i++) {
            double min = Double.MAX_VALUE;
            for (int j = 0; j < rows; j++) {
                if (min > cost[j][i])
                    min = cost[j][i];
            }

            for (int j = 0; j < rows; j++) {
                cost[j][i] = cost[j][i] - min;
            }
        }
    }

    public static int countZerosInRow(Double[][] cost, int columns, int rowId, MutableInteger col, int[][] isTraversed) {
        int count = 0;

        for (int i = 0; i < columns; i++) {
            if (isTraversed[rowId][i] == 0 && cost[rowId][i] == 0) {
                col.setValue(i);
                count++;
            }
        }

        return count;
    }

    public static int countZerosInColumn(Double[][] cost, int rows, int colId, MutableInteger row, int[][] isTraversed) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            if (isTraversed[i][colId] == 0 && cost[i][colId] == 0) {
                count++;

                row.setValue(i);
            }
        }
        return count;
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
