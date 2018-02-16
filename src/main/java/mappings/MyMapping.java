package mappings;

import attributes.IAttribute;
import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import javafx.util.Pair;
import strategies.IStrategy;
import utils.ScoreComputer;

import java.util.ArrayList;

/**
 * Created by astha.a on 13/02/18.
 */
public class MyMapping implements Mapping {
    private ArrayList<IAttribute> attributes;
    private IStrategy strategy;
    public ArrayList<OrderAssignment> getMapping(ArrayList<Order> orders, ArrayList<DeliveryExec> deliveryExec) {
        ScoreComputer scoreComputer = new ScoreComputer();
        for (IAttribute attribute : attributes) {
            ArrayList<Pair<OrderAssignment, Double>> attributeScore = attribute.getNormalisedScore(orders, deliveryExec);
            scoreComputer.updateScores(attributeScore, attribute.getWeight());
        }



        return strategy.getFinalAssignment(scoreComputer);
    }
}
