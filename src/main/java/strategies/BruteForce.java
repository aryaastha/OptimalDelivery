package strategies;

import beans.OrderAssignment;
import utils.ScoreComputer;

import java.util.ArrayList;

/**
 * Created by astha.a on 14/02/18.
 */
public class BruteForce implements IStrategy {

    @Override
    public ArrayList<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) {
        //n! solution, hence not implementing
        return null;
    }
}
