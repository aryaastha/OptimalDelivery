import beans.*;
import com.google.gson.JsonObject;
import mappings.Mapping;
import org.junit.Test;
import utils.FileUtils;
import utils.GsonFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by astha.a on 15/02/18.
 */
public class StrategyTest {
    public List<OrderAssignment> runTest(String mappingParameters) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        JsonObject jsonObject = GsonFactory.getInstance().getGson().fromJson(mappingParameters, JsonObject.class);
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


        List<OrderAssignment> mapping = myImplementation.getMapping(orders,executives);
        return mapping;
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

        List<OrderAssignment> orderAssignmentsForDp = runTest(mappingForDp);
        HashMap<Order, DeliveryExec> expectedAnswerForDp = new HashMap<>();

        expectedAnswerForDp.put(order1,exec1);
        expectedAnswerForDp.put(order3,exec3);
        expectedAnswerForDp.put(order2,exec2);

        for (OrderAssignment assign : orderAssignmentsForDp){
            assertEquals(expectedAnswerForDp.get(assign.getOrder()),assign.getDeliveryExec());
        }
    }

    @Test
    public void testGreedy() throws Exception {
        String mappingForGreedy = FileUtils.readFromFile("src/test/resources/attributes2.json");;

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

        List<OrderAssignment> orderAssignmentsForGreedy = runTest(mappingForGreedy);

        HashMap<Order, DeliveryExec> expectedAnswerForGreedy = new HashMap<>();

        expectedAnswerForGreedy.put(order1,exec3);
        expectedAnswerForGreedy.put(order2,exec2);
        expectedAnswerForGreedy.put(order3,exec1);

        for (OrderAssignment assign : orderAssignmentsForGreedy){
            assertEquals(expectedAnswerForGreedy.get(assign.getOrder()),assign.getDeliveryExec());
        }
    }

    @Test
    public void testLp() throws Exception {
        String mappingForLp = FileUtils.readFromFile("src/test/resources/attributes3.json");

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


        List<OrderAssignment> orderAssignmentsForLp = runTest(mappingForLp);
        HashMap<Order, DeliveryExec> expectedAnswerForLp = new HashMap<>();

        expectedAnswerForLp.put(order1,exec1);
        expectedAnswerForLp.put(order3,exec3);
        expectedAnswerForLp.put(order2,exec2);

        for (OrderAssignment assign : orderAssignmentsForLp){
            assertEquals(expectedAnswerForLp.get(assign.getOrder()),assign.getDeliveryExec());
        }
    }
}
