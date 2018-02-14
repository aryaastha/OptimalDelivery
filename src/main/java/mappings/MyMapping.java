package mappings;

import attributes.IAttribute;
import beans.Assignment;
import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import strategies.IStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public HashMap<Order, DeliveryExec> getMapping(ArrayList<Order> orders, ArrayList<DeliveryExec> deliveryExec) {
        LinkedHashMap<OrderAssignment, Double> allCombinationScoreList = new LinkedHashMap<>();
        for (IAttribute attribute : attributes){
            ArrayList<Assignment> score = attribute.getScore(orders, deliveryExec);

            for (Assignment assign : score) {
                if (allCombinationScoreList.containsKey(assign.getPair())){
                    allCombinationScoreList.put(assign.getPair(),assign.getCost()+allCombinationScoreList.get(assign.getPair()));
                }else allCombinationScoreList.put(assign.getPair(),assign.getCost());
            }
        }

        return strategy.getFinalAssignment(allCombinationScoreList);
    }
}
