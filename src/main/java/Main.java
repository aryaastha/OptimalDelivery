import beans.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mappings.Mapping;
import utils.FileUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by astha.a on 14/02/18.
 */
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        String mappingParameters = FileUtils.readFromFile("/Users/astha.a/work/TestCode/src/main/resources/attributes.json");
        JsonObject jsonObject = new Gson().fromJson(mappingParameters, JsonObject.class);
        Class implementation = Class.forName("mappings." + jsonObject.get("implementation").getAsString());
        Constructor declaredConstructor = implementation.getDeclaredConstructor(String.class);
        Mapping myImplementation = (Mapping) declaredConstructor.newInstance(jsonObject.get("properties").toString());


        ArrayList<Order> orders = new ArrayList<Order>();


        int t = 0;
        for(Double i = 0D; i<2D; i++){
            orders.add(new Order(++t,new Restaurant(new Location(Math.random(),Math.random())),Math.random()));
        }

        ArrayList<DeliveryExec> executives = new ArrayList<DeliveryExec>();

        for (Double i = 0D ; i < 2D ; i++ ){
            executives.add(new DeliveryExec(++t,new Location(Math.random(),Math.random()),Math.random()));
        }


        ArrayList<OrderAssignment> mapping = myImplementation.getMapping(orders,executives);
        System.out.println("Order Assignment : \n");
        for (OrderAssignment assign : mapping){
            System.out.println(assign);
        }
    }
}
