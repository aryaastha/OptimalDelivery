package mappings;

import attributes.IAttribute;
import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;
import strategies.IStrategy;
import utils.ScoreComputer;

import java.util.List;

/**
 * Created by astha.a on 13/02/18.
 */
public class MyMapping implements Mapping {
    private List<IAttribute> attributes;
    private IStrategy strategy;

    public List<OrderAssignment> getMapping(List<Order> orders, List<DeliveryExec> deliveryExec) throws Exception {
        ScoreComputer scoreComputer = new ScoreComputer();
        for (IAttribute attribute : attributes) {
            List<Pair<OrderAssignment, Double>> attributeScore = attribute.getNormalisedScore(orders, deliveryExec);
            scoreComputer.updateScores(attributeScore, attribute.getWeight());
        }

        return strategy.getFinalAssignment(scoreComputer);
    }
}
