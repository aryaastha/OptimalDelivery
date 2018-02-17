package strategies;

import beans.OrderAssignment;
import utils.ScoreComputer;

import java.util.List;

/**
 * Created by astha.a on 14/02/18.
 */
public class BruteForce implements IStrategy {

    @Override
    public List<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores) {
        //n! solution, hence not implementing
        return null;
    }
}
