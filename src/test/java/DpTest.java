import beans.*;
import com.google.gson.JsonObject;
import mappings.Mapping;
import org.junit.Test;
import utils.FileUtils;
import utils.GsonFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by astha.a on 16/02/18.
 */
public class DpTest {
    public List<OrderAssignment> runTest(String mappingParameters) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        JsonObject jsonObject = GsonFactory.getInstance().getGson().fromJson(mappingParameters, JsonObject.class);
        Mapping myImplementation = GsonFactory.getInstance().getGson().fromJson(jsonObject, Mapping.class);
        List<Order> listOfOrders = new ArrayList<Order>();


        int t = 0;

        listOfOrders.add(new Order(++t,new Restaurant(new Location(2D,5D)),9D));
        listOfOrders.add(new Order(++t,new Restaurant(new Location(2D,4D)),7D));
        listOfOrders.add(new Order(++t,new Restaurant(new Location(1D,4D)),9D));
//        listOfOrders.add(new Order(++t,new Restaurant(new Location(1D,4D)),9D));

        List<DeliveryExec> listOfExecs = new ArrayList<DeliveryExec>();

        listOfExecs.add(new DeliveryExec(++t,new Location(6D,8D),5D));
        listOfExecs.add(new DeliveryExec(++t,new Location(4D,1D),2D));
        listOfExecs.add(new DeliveryExec(++t,new Location(9D,10D),3D));
        listOfExecs.add(new DeliveryExec(++t,new Location(9D,10D),3D));

        return myImplementation.getMapping(listOfOrders,listOfExecs);
    }

    @Test
    public void testDp() throws Exception {
        String mappingForDp = FileUtils.readFromFile("src/test/resources/attributes.json");

        Order order1 = GsonFactory.getInstance().getGson().fromJson(" {\n" +
                " \t\"orderId\": 2,\n" +
                " \t\"restaurant\": {\n" +
                " \t\t\"restaurantLocation\": {\n" +
                " \t\t\t\"latitude\": 2.0,\n" +
                " \t\t\t\"longitude\": 4.0\n" +
                " \t\t}\n" +
                " \t},\n" +
                " \t\"orderedTime\": 7.0\n" +
                " }", Order.class);

        Order order2 = GsonFactory.getInstance().getGson().fromJson(" {\n" +
                " \t\"orderId\": 1,\n" +
                " \t\"restaurant\": {\n" +
                " \t\t\"restaurantLocation\": {\n" +
                " \t\t\t\"latitude\": 2.0,\n" +
                " \t\t\t\"longitude\": 5.0\n" +
                " \t\t}\n" +
                " \t},\n" +
                " \t\"orderedTime\": 9.0\n" +
                " }", Order.class);

        Order order3 = GsonFactory.getInstance().getGson().fromJson(" {\n" +
                " \t\"orderId\": 3,\n" +
                " \t\"restaurant\": {\n" +
                " \t\t\"restaurantLocation\": {\n" +
                " \t\t\t\"latitude\": 1.0,\n" +
                " \t\t\t\"longitude\": 4.0\n" +
                " \t\t}\n" +
                " \t},\n" +
                " \t\"orderedTime\": 9.0\n" +
                " }",Order.class);

        DeliveryExec exec1 = GsonFactory.getInstance().getGson().fromJson("{\n" +
                "\t\"id\": 6,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 9.0,\n" +
                "\t\t\"longitude\": 10.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 3.0\n" +
                "}", DeliveryExec.class);

        DeliveryExec exec2 = GsonFactory.getInstance().getGson().fromJson("{\n" +
                "\t\"id\": 5,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 6.0,\n" +
                "\t\t\"longitude\": 8.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 5.0\n" +
                "}", DeliveryExec.class);

        DeliveryExec exec3 = GsonFactory.getInstance().getGson().fromJson("{\n" +
                "\t\"id\": 4,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 4.0,\n" +
                "\t\t\"longitude\": 1.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 2.0\n" +
                "}", DeliveryExec.class);

        DeliveryExec exec4 = GsonFactory.getInstance().getGson().fromJson("{\n" +
                "\t\"id\": 7,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 4.0,\n" +
                "\t\t\"longitude\": 8.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 1.0\n" +
                "}", DeliveryExec.class);

        List<OrderAssignment> orderAssignmentsForDp = runTest(mappingForDp);
//        Map<Order, DeliveryExec> expectedAnswerForDp = new HashMap<>();
//
//        expectedAnswerForDp.put(order1,exec1);
//        expectedAnswerForDp.put(order3,exec3);
//        expectedAnswerForDp.put(order2,exec2);

        for (OrderAssignment assign : orderAssignmentsForDp){
            System.out.println(assign);
//            assertEquals(expectedAnswerForDp.get(assign.getOrder()),assign.getDeliveryExec());
        }
    }
}
