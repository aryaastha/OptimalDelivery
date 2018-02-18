package strategies;

import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import org.apache.commons.lang.mutable.MutableInt;
import utils.AssignmentHelper;
import utils.ScoreComputer;

import java.util.Arrays;
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
        Integer size = helper.getSize();
        double[][] cost = helper.getCostArray();

        simplifyRows(cost, size, size);

        simplifyColumns(cost, size, size);

        Set<OrderAssignment> optimalAssignments = new HashSet<>();

        while (optimalAssignments.size() != size) {
            optimalAssignments.clear();
            int[][] isTraversed = new int[size][size];
            for (int[] i : isTraversed)
                Arrays.fill(i, 0);

            int oldValue;

            do {
                oldValue = optimalAssignments.size();

                for (int i = 0; i < size; i++) {
                    MutableInt columnId = new MutableInt(0);
                    if (countZerosInRow(cost, size, i, columnId, isTraversed) == 1) {
                        optimalAssignments.add(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(columnId.intValue())));
                        for (int j = 0; j < size; j++) {
                            isTraversed[j][columnId.intValue()]++;
                        }
                    }
                }

                for (int i = 0; i < size; i++) {
                    MutableInt rowId = new MutableInt(0);
                    if (countZerosInColumn(cost, size, i, rowId, isTraversed) == 1) {
                        optimalAssignments.add(new OrderAssignment(listOfOrders.get(rowId.intValue()), listOfExecs.get(i)));
                        for (int j = 0; j < size; j++) {
                            isTraversed[rowId.intValue()][j]++;
                        }
                    }

                }
            }while (oldValue!=optimalAssignments.size());

            int countOfNumberOfZeroes = 0;

            for (int i = 0; i < size; i++) {
                countOfNumberOfZeroes += countZerosInRow(cost, size, i, new MutableInt(0), isTraversed);
            }


            if (countOfNumberOfZeroes > 0) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (cost[i][j] == 0 && isTraversed[i][j] == 0) {
                            optimalAssignments.add(new OrderAssignment(listOfOrders.get(i), listOfExecs.get(j)));
                            for (int o = 0; o < size; o++) {
                                isTraversed[o][j]++;
                            }
                            int k = j + 1;
                            int l = i + 1;
                            while (k < size && l < size) {
                                if (cost[l][k] == 0 && isTraversed[l][k] == 0) {
                                    optimalAssignments.add(new OrderAssignment(listOfOrders.get(l), listOfExecs.get(k)));
                                    for (int o = 0; o < size; o++) {
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
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (isTraversed[i][j] == 0 && min > cost[i][j]) {
                        min = cost[i][j];
                    }
                }
            }


            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
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

    public static int countZerosInRow(double[][] cost, int columns, int rowId, MutableInt col, int[][] isTraversed) {
        int count = 0;

        for (int i = 0; i < columns; i++) {
            if (isTraversed[rowId][i] == 0 && cost[rowId][i] == 0) {
                col.setValue(i);
                count++;
            }
        }

        return count;
    }

    public static int countZerosInColumn(double[][] cost, int rows, int colId, MutableInt row, int[][] isTraversed) {
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
