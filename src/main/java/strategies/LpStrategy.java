package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import sun.java2d.xr.MutableInteger;
import utils.AssignmentHelper;
import utils.ScoreComputer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by astha.a on 16/02/18.
 */
public class LpStrategy implements IStrategy {


    @Override
    public List<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) {
        AssignmentHelper helper = new AssignmentHelper(updatedScores);
        List<Order> listOfOrders = helper.getOrders();
        List<DeliveryExec> listOfExecs = helper.getDeliveryExecs();

        double[][] cost = helper.getCostArray();

        simplifyRows(cost, helper.getSize(), helper.getSize());

        simplifyColumns(cost, helper.getSize(), helper.getSize());

        Set<OrderAssignment> optimalAssignments = new HashSet<>();

        while (optimalAssignments.size() != helper.getSize()) {
            optimalAssignments.clear();
            int[][] isTraversed = new int[helper.getSize()][helper.getSize()];

            for (int i = 0; i < helper.getSize(); i++) {
                for (int j = 0; j < helper.getSize(); j++) {
                    isTraversed[i][j] = 0;
                }
            }


            for (int i = 0; i < helper.getSize(); i++) {
                MutableInteger columnId = new MutableInteger(0);
                if (countZerosInRow(cost, helper.getSize(), i, columnId, isTraversed) == 1) {
                    optimalAssignments.add(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(columnId.getValue())));
                    for (int j = 0; j < helper.getSize(); j++) {
                        isTraversed[j][columnId.getValue()]++;
                    }
                }
            }

            for (int i = 0; i < helper.getSize(); i++) {
                MutableInteger rowId = new MutableInteger(0);
                if (countZerosInColumn(cost, helper.getSize(), i, rowId, isTraversed) == 1) {
                    optimalAssignments.add(new OrderAssignment(listOfOrders.get(rowId.getValue()), listOfExecs.get(i)));
                    for (int j = 0; j < helper.getSize(); j++) {
                        isTraversed[rowId.getValue()][j]++;
                    }
                }

            }

            int countOfNumberOfZeroes = 0;

            for (int i = 0; i < helper.getSize(); i++) {
                countOfNumberOfZeroes += countZerosInRow(cost, helper.getSize(), i, new MutableInteger(0), isTraversed);
            }


            if (countOfNumberOfZeroes > 0) {
                for (int i = 0; i < helper.getSize(); i++) {
                    for (int j = 0; j < helper.getSize(); j++) {
                        if (cost[i][j] == 0 && isTraversed[i][j] == 0) {
                            optimalAssignments.add(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(j)));
                            for (int o = 0; o < helper.getSize(); o++) {
                                isTraversed[o][j]++;
                            }
                            int k = j + 1;
                            int l = i + 1;
                            while (k < helper.getSize() && l < helper.getSize()) {
                                if (cost[l][k] == 0 && isTraversed[l][k] == 0) {
                                    optimalAssignments.add(new OrderAssignment(listOfOrders.get(l), listOfExecs.get(k)));
                                    for (int o = 0; o < helper.getSize(); o++) {
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
            for (int i = 0; i < helper.getSize(); i++) {
                for (int j = 0; j < helper.getSize(); j++) {
                    if (isTraversed[i][j] == 0 && min > cost[i][j]) {
                        min = cost[i][j];
                    }
                }
            }


            for (int i = 0; i < helper.getSize(); i++) {
                for (int j = 0; j < helper.getSize(); j++) {
                    if (isTraversed[i][j] > 1) {
                        cost[i][j] += min;
                    } else if (isTraversed[i][j] == 0) {
                        cost[i][j] -= min;
                    }
                }
            }
        }
        return helper.filterDummies(optimalAssignments);
    }

    public static void simplifyRows(double[][] cost, int rows, int columns) {
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

    public static void simplifyColumns(double[][] cost, int rows, int columns) {
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

    public static int countZerosInRow(double[][] cost, int columns, int rowId, MutableInteger col, int[][] isTraversed) {
        int count = 0;

        for (int i = 0; i < columns; i++) {
            if (isTraversed[rowId][i] == 0 && cost[rowId][i] == 0) {
                col.setValue(i);
                count++;
            }
        }

        return count;
    }

    public static int countZerosInColumn(double[][] cost, int rows, int colId, MutableInteger row, int[][] isTraversed) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            if (isTraversed[i][colId] == 0 && cost[i][colId] == 0) {
                count++;

                row.setValue(i);
            }
        }
        return count;
    }
}
