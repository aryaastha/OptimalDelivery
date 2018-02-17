package strategies;

import beans.OrderAssignment;
import utils.ScoreComputer;

import java.util.List;

/**
 * Created by astha.a on 14/02/18.
 */
public interface IStrategy {
    List<OrderAssignment> getFinalAssignment(ScoreComputer updatedScores);
}
