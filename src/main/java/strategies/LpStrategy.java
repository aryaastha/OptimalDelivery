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
    private double[][] cost;
    private int[][] linesArray;
    private Integer size;
    private List<Order> orders;
    private List<DeliveryExec> executives;


    @Override
    public List<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) throws Exception {
        AssignmentHelper helper = new AssignmentHelper(updatedScores);
        orders = helper.getOrders();
        executives = helper.getDeliveryExecs();
        size = helper.getSize();
        cost = helper.getCostArray();

        simplifyRows(cost, size, size);

        simplifyColumns(cost, size, size);

        Set<OrderAssignment> optimalAssignments = new HashSet<>();

        while (optimalAssignments.size() != size) {
            optimalAssignments.clear();
            linesArray = new int[size][size];
            for (int[] i : linesArray)
                Arrays.fill(i, 0);

            drawMinimalLines(optimalAssignments);

            introduceZeroes();
        }

        return helper.filterDummies(optimalAssignments);
    }


    private void simplifyRows(double[][] cost, int rows, int columns) {
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

    private void simplifyColumns(double[][] cost, int rows, int columns) {
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

    private int countZerosInRow(int rowId, MutableInt col) {
        int count = 0;

        for (int i = 0; i < size; i++) {
            if (linesArray[rowId][i] == 0 && cost[rowId][i] == 0) {
                col.setValue(i);
                count++;
            }
        }

        return count;
    }

    private void introduceZeroes() {
        double min = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (linesArray[i][j] == 0 && min > cost[i][j]) {
                    min = cost[i][j];
                }
            }
        }


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (linesArray[i][j] > 1) {
                    cost[i][j] += min;
                } else if (linesArray[i][j] == 0) {
                    cost[i][j] -= min;
                }
            }
        }
    }

    private int countZerosInColumn(int colId, MutableInt row) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (linesArray[i][colId] == 0 && cost[i][colId] == 0) {
                count++;

                row.setValue(i);
            }
        }
        return count;
    }

    private void drawMinimalLines(Set<OrderAssignment> optimalAssignments) {
        int oldValue;
        do {
            oldValue = optimalAssignments.size();

            for (int i = 0; i < size; i++) {
                MutableInt columnId = new MutableInt(0);
                if (countZerosInRow(i, columnId) == 1) {
                    optimalAssignments.add(new OrderAssignment(orders.get(i), executives.get(columnId.intValue())));
                    drawVerticalLine(columnId.intValue());
                }
            }

            for (int i = 0; i < size; i++) {
                MutableInt rowId = new MutableInt(0);
                if (countZerosInColumn(i, rowId) == 1) {
                    optimalAssignments.add(new OrderAssignment(orders.get(rowId.intValue()), executives.get(i)));
                    drawHorizontalLine(rowId.intValue());
                }

            }
        } while (oldValue != optimalAssignments.size());

        int countOfNumberOfZeroes = 0;

        for (int i = 0; i < size; i++) {
            countOfNumberOfZeroes += countZerosInRow(i, new MutableInt(0));
        }


        if (countOfNumberOfZeroes > 0) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (cost[i][j] == 0 && linesArray[i][j] == 0) {
                        optimalAssignments.add(new OrderAssignment(orders.get(i), executives.get(j)));
                        drawVerticalLine(j);
                        int k = j + 1;
                        int l = i + 1;
                        while (k < size && l < size) {
                            if (cost[l][k] == 0 && linesArray[l][k] == 0) {
                                optimalAssignments.add(new OrderAssignment(orders.get(l), executives.get(k)));
                                drawVerticalLine(k);
                            }
                            k++;
                            l++;
                        }
                        break;
                    }
                }
            }
        }
    }

    private void drawHorizontalLine(int rowid) {
        for (int i = 0; i < size; i++) {
            linesArray[rowid][i]++;
        }
    }

    private void drawVerticalLine(int columnId) {
        for (int i = 0; i < size; i++) {
            linesArray[i][columnId]++;
        }
    }
}
