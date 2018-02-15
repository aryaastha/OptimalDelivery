package strategies;

import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by astha.a on 14/02/18.
 */
public interface IStrategy {
    public ArrayList<OrderAssignment> getFinalAssignment(ArrayList<Pair<OrderAssignment, Double>> allCombinationScoreList);
}
