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
//            orders.add(new Order(++t,new Restaurant(new Location(i,i)),i));
        }

        orders.add(new Order(++t,new Restaurant(new Location(2D,5D)),9D));
        orders.add(new Order(++t,new Restaurant(new Location(2D,4D)),7D));
        orders.add(new Order(++t,new Restaurant(new Location(1D,4D)),9D));

        ArrayList<DeliveryExec> executives = new ArrayList<DeliveryExec>();

        for (Double i = 0D ; i<2D ; i++ ){
//            executives.add(new DeliveryExec(++t,new Location(i,i),i));
        }

        executives.add(new DeliveryExec(++t,new Location(4D,1D),2D));
        executives.add(new DeliveryExec(++t,new Location(6D,8D),5D));
        executives.add(new DeliveryExec(++t,new Location(9D,10D),3D));


        ArrayList<OrderAssignment> mapping = myImplementation.getMapping(orders,executives);
        System.out.println("Order Assignment : \n");
        for (OrderAssignment assign : mapping){
            System.out.println(assign);
        }
    }
}
