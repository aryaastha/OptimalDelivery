package mappings;

import attributes.IAttribute;
import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.util.Pair;
import strategies.IStrategy;
import utils.ScoreComputer;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by astha.a on 13/02/18.
 */
public class MyMapping implements Mapping {
    private ArrayList<IAttribute> attributes;
    private IStrategy strategy;

    public MyMapping(String prop) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.attributes = new ArrayList<IAttribute>();
        JsonObject properties = new Gson().fromJson(prop, JsonObject.class);
        for (JsonElement entry : properties.get("attributes").getAsJsonArray()) {
            Class attributeName = Class.forName("attributes." + entry.getAsJsonObject().get("name").getAsString());
            attributes.add((IAttribute) new Gson().fromJson(entry.getAsJsonObject().get("properties"), attributeName));
        }


        Class strategyName = Class.forName("strategies." + properties.get("strategy").getAsJsonObject().get("name").getAsString());
        this.strategy = (IStrategy) new Gson().fromJson(properties.get("strategy").getAsJsonObject().get("properties"), strategyName);
    }

    public ArrayList<OrderAssignment> getMapping(ArrayList<Order> orders, ArrayList<DeliveryExec> deliveryExec) {
        ScoreComputer scoreComputer = new ScoreComputer();
        for (IAttribute attribute : attributes) {
            ArrayList<Pair<OrderAssignment, Double>> attributeScore = attribute.getNormalisedScore(orders, deliveryExec);
            scoreComputer.updateScores(attributeScore, attribute.getWeight());
        }

        return strategy.getFinalAssignment(scoreComputer);
    }
}
