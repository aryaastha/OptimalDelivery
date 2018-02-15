package strategies;

import beans.OrderAssignment;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by astha.a on 14/02/18.
 */
public class BruteForce implements IStrategy{

    public BruteForce() {}

    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(ArrayList<Pair<OrderAssignment, Double>> allCombinationScoreList) {
        return null;
    }
}
