import beans.DeliveryExec;
import beans.Location;
import beans.Order;
import beans.Restaurant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mappings.Mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by astha.a on 14/02/18.
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String mappingParameters = "{\n" +
                "  \"implementation\":\"MyMapping\",\n" +
                "  \"properties\": {\n" +
                "    \"attributes\": {\n" +
                "      \"DeLastOrderTime\": 1,\n" +
                "      \"OrderTime\": 1,\n" +
                "      \"MinimumDistance\": 1\n" +
                "    },\n" +
                "    \"strategy\": \"GreedyStrategy\"\n" +
                "  }\n" +
                "}";

        JsonObject jsonObject = new Gson().fromJson(mappingParameters, JsonObject.class);
        Class implementation = Class.forName("mappings." + jsonObject.get("implementation").getAsString());
        Constructor declaredConstructor = implementation.getDeclaredConstructor(JsonObject.class);
        Mapping mappingImplementation = (Mapping)declaredConstructor.newInstance(jsonObject.get("properties").getAsJsonObject());


        ArrayList<Order> orders = new ArrayList<Order>();
        int t = 0;
        for(Double i = 0D; i<10D; i++){
            orders.add(new Order(++t,new Restaurant(new Location(i+1,i+2)),i+1));
        }

        ArrayList<DeliveryExec> executives = new ArrayList<DeliveryExec>();

        for (Double i = 0D ; i < 10D ; i++ ){
            executives.add(new DeliveryExec(++t,new Location(i,i),i));
        }

        HashMap<Order, DeliveryExec> mapping = mappingImplementation.getMapping(orders,executives);
        for (Map.Entry<Order, DeliveryExec> map : mapping.entrySet()){
            System.out.println(map.getKey().getOrderId() + " : " + map.getValue().getId());
        }
    }
}
