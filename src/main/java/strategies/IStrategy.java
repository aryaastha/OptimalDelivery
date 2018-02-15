package strategies;

import beans.OrderAssignment;
import utils.UpdateScores;

import java.util.ArrayList;

/**
 * Created by astha.a on 14/02/18.
 */
public interface IStrategy {
    ArrayList<OrderAssignment> getFinalAssignment(UpdateScores updatedScores);
}
