package strategies;

import beans.OrderAssignment;
import utils.ScoreComputer;

import java.util.ArrayList;

/**
 * Created by astha.a on 14/02/18.
 */
public interface IStrategy {
    ArrayList<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores);
}
