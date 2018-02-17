import beans.*;
import com.google.gson.JsonObject;
import mappings.Mapping;
import utils.FileUtils;
import utils.GsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by astha.a on 16/02/18.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String attributesString = FileUtils.readFromFile("/Users/astha.a/work/TestCode/src/resources/attributes.json");
        JsonObject jsonObject = GsonFactory.getInstance().getGson().fromJson(attributesString, JsonObject.class);
        Mapping myImplementation = GsonFactory.getInstance().getGson().fromJson(jsonObject, Mapping.class);
        List<Order> orders = new ArrayList<Order>();


        int t = 0;

        orders.add(new Order(++t,new Restaurant(new Location(2D,5D)),9D));
        orders.add(new Order(++t,new Restaurant(new Location(2D,4D)),7D));
        orders.add(new Order(++t,new Restaurant(new Location(1D,4D)),9D));

        List<DeliveryExec> executives = new ArrayList<DeliveryExec>();

        executives.add(new DeliveryExec(++t,new Location(4D,1D),2D));
        executives.add(new DeliveryExec(++t,new Location(6D,8D),5D));
        executives.add(new DeliveryExec(++t,new Location(9D,10D),3D));
        executives.add(new DeliveryExec(++t,new Location(4D,8D),1D));

        List<OrderAssignment> mapping = myImplementation.getMapping(orders, executives);
        mapping.forEach(System.out::println);
    }
}
