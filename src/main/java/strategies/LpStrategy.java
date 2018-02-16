package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;
import sun.java2d.xr.MutableInteger;
import utils.ScoreComputer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by astha.a on 16/02/18.
 */
public class LpStrategy implements  IStrategy{



    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) {
        HashMap<OrderAssignment, Double> allCombinationScoreList = updatedScores.getScores();

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

        Double[][] cost = new Double[listOfOrders.size()][listOfExecs.size()];
        for (int i = 0; i < listOfOrders.size(); i++) {
            for (int j = 0; j < listOfExecs.size(); j++) {
                cost[i][j] = allCombinationScoreList.get(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(j)));
            }
        }

        simplifyRows(cost, listOfOrders.size(), listOfExecs.size());

        simplifyColumns(cost, listOfOrders.size(), listOfExecs.size());


        ArrayList<Pair<Integer, Integer>> optimalPairs = new ArrayList<>();

        while (optimalPairs.size()!=listOfOrders.size()) {
            optimalPairs.clear();
            int[][] isTraversed = new int[listOfOrders.size()][listOfExecs.size()];

            for (int i = 0; i < listOfOrders.size(); i++) {
                for (int j = 0; j < listOfExecs.size(); j++) {
                    isTraversed[i][j] = 0;
                }
            }

            for (int i = 0; i < listOfOrders.size(); i++) {
                MutableInteger columnId = new MutableInteger(0);
                if (countZerosInRow(cost, listOfExecs.size(), i, columnId, isTraversed) == 1) {
                    optimalPairs.add(new Pair<>(i, columnId.getValue()));
                    for (int j = 0; j < listOfOrders.size(); j++) {
                        isTraversed[j][columnId.getValue()]++;
                    }
                }
            }

            for (int i = 0; i < listOfExecs.size(); i++) {
                MutableInteger rowId = new MutableInteger(0);
                if (countZerosInColumn(cost, listOfOrders.size(), i, rowId, isTraversed) == 1) {
                    optimalPairs.add(new Pair<>(rowId.getValue(), i));
                    for (int j = 0; j < listOfExecs.size(); j++) {
                        isTraversed[rowId.getValue()][j]++;
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

        for (Pair<Integer, Integer> p : optimalPairs ){
            finalAssignment.add(new OrderAssignment(listOfOrders.get(p.getKey()),listOfExecs.get(p.getValue())));
        }




        return finalAssignment;
    }

    public void simplifyRows(Double[][] cost, int rows, int columns){
        for (int i = 0; i < rows; i++) {
            double min = Double.MAX_VALUE;
            for (int j = 0; j < columns; j++) {
                if(min > cost[i][j])
                    min = cost[i][j];
            }

            for (int j = 0; j < columns; j++) {
                cost[i][j] = cost[i][j] - min;
            }
        }
    }

    public void simplifyColumns(Double[][] cost, int rows, int columns){
        for (int i = 0; i < columns; i++) {
            double min = Double.MAX_VALUE;
            for (int j = 0; j < rows; j++) {
                if(min > cost[j][i])
                    min = cost[j][i];
            }

            for (int j = 0; j < rows; j++) {
                cost[j][i] = cost[j][i] - min;
            }
        }
    }

    public int countZerosInRow(Double[][] cost, int columns, int rowId, MutableInteger col, int[][] isTraversed){
        int count = 0;

        for(int i=0;i<columns;i++){
            if (isTraversed[rowId][i]==0 && cost[rowId][i] == 0){
                col.setValue(i);
                count++;
            }
        }

        return count;
    }

    public int countZerosInColumn(Double[][] cost, int rows, int colId, MutableInteger row, int[][] isTraversed){
        int count = 0;

        for(int i=0;i<rows;i++){
            if (isTraversed[i][colId]==0 && cost[i][colId] == 0){
                count++;

                row.setValue(i);
            }
        }
        return count;
    }
}
