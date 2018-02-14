package mappings;

import attributes.IAttribute;
import beans.Assignment;
import beans.DeliveryExec;
import beans.Order;
import beans.OrderAssignment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by astha.a on 13/02/18.
 */
public class MyMapping implements Mapping{
    private ArrayList<IAttribute> attributes;

    public MyMapping(String jsonAttributes) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.attributes = new ArrayList<IAttribute>();
        JsonObject jsonObject = new Gson().fromJson(jsonAttributes, JsonObject.class);
        for(Map.Entry<String, JsonElement> entry : jsonObject.entrySet()){
            Class attributeName = Class.forName("attributes." + entry.getKey());
            Constructor declaredConstructor = attributeName.getDeclaredConstructor(Double.class);
            attributes.add((IAttribute)declaredConstructor.newInstance(entry.getValue().getAsDouble()));
        }
        System.out.println("Size of attributes array is : " + attributes.size());
    }

    public HashMap<Order, DeliveryExec> getMapping(ArrayList<Order> orders, ArrayList<DeliveryExec> deliveryExec) {
        HashMap<Order, DeliveryExec> finalAssignment = new HashMap<Order, DeliveryExec>();

        ArrayList<Assignment> allCombinationScoreList = new ArrayList<Assignment>();
        for (IAttribute attribute : attributes){
            allCombinationScoreList.addAll(attribute.getAttributeWiseScore(orders, deliveryExec));
        }




        Map<OrderAssignment, Map<Double, List<Assignment>>> collection = allCombinationScoreList.stream().collect(Collectors.groupingBy(Assignment::getPair, Collectors.groupingBy(Assignment::getCost)));



        System.out.println(collection);


//
//        Collections.sort(allCombinationScoreList, new Comparator<Assignment>() {
//            public int compare(Assignment o1, Assignment o2) {
//                return o1.getCost().compareTo(o2.getCost());
//            }
//        });

//        for (Assignment ass : allCombinationScoreList) {
//            if (!finalAssignment.containsKey(ass.getOrder())){
//                finalAssignment.put(ass.getOrder(), ass.getDeliveryExec());
//            }else{
//                finalAssignment.put(finalAssignment.get(ass.getOrder()) + )
//            }
//        }

        return finalAssignment;
    }
}
