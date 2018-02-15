package mappings;

import attributes.IAttribute;
import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.util.Pair;
import strategies.IStrategy;
import utils.UpdateScores;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by astha.a on 13/02/18.
 */
public class MyMapping implements Mapping{
    private ArrayList<IAttribute> attributes;
    private IStrategy strategy;

    public MyMapping(JsonObject properties) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.attributes = new ArrayList<IAttribute>();
        for(Map.Entry<String, JsonElement> entry : properties.get("attributes").getAsJsonObject().entrySet()){
            Class attributeName = Class.forName("attributes." + entry.getKey());
            Constructor declaredConstructor = attributeName.getDeclaredConstructor(Double.class);
            attributes.add((IAttribute)declaredConstructor.newInstance(entry.getValue().getAsDouble()));
        }

        Class strategyName = Class.forName("strategies." + properties.get("strategy").getAsString());
        Constructor declaredConstructor = strategyName.getDeclaredConstructor();
        this.strategy = (IStrategy) declaredConstructor.newInstance();


    }

    public ArrayList<OrderAssignment> getMapping(ArrayList<Order> orders, ArrayList<DeliveryExec> deliveryExec) {
        UpdateScores updateScores = new UpdateScores();
        for (IAttribute attribute : attributes){
            ArrayList<Pair<OrderAssignment, Double>> attributeScore = attribute.getScore(orders, deliveryExec);
            updateScores.updateScores(attributeScore, attribute.getWeight());
        }

        return strategy.getFinalAssignment(updateScores.getUpdatedScores());
    }
}
