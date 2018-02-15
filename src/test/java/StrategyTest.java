import beans.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mappings.Mapping;
import org.junit.Test;
import utils.FileUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by astha.a on 15/02/18.
 */
public class StrategyTest {
    public ArrayList<OrderAssignment> runTest(String mappingParameters) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
//        System.out.println("Order Assignment : \n");
//        for (OrderAssignment assign : mapping){
//            System.out.println(assign);
//        }

        return mapping;
    }

    @Test
    public void testStrategies() throws Exception {
        String mappingForDp = FileUtils.readFromFile("/Users/astha.a/work/TestCode/src/test/resources/attributes.json");
        String mappingForGreedy = FileUtils.readFromFile("/Users/astha.a/work/TestCode/src/test/resources/attributes2.json");;

        Order order1 = new Gson().fromJson(" {\n" +
                " \t\"orderId\": 2,\n" +
                " \t\"restaurant\": {\n" +
                " \t\t\"restaurantLocation\": {\n" +
                " \t\t\t\"latitude\": 2.0,\n" +
                " \t\t\t\"longitude\": 4.0\n" +
                " \t\t}\n" +
                " \t},\n" +
                " \t\"orderedTime\": 7.0\n" +
                " }", Order.class);

        Order order2 = new Gson().fromJson(" {\n" +
                " \t\"orderId\": 1,\n" +
                " \t\"restaurant\": {\n" +
                " \t\t\"restaurantLocation\": {\n" +
                " \t\t\t\"latitude\": 2.0,\n" +
                " \t\t\t\"longitude\": 5.0\n" +
                " \t\t}\n" +
                " \t},\n" +
                " \t\"orderedTime\": 9.0\n" +
                " }", Order.class);

        Order order3 = new Gson().fromJson(" {\n" +
                " \t\"orderId\": 3,\n" +
                " \t\"restaurant\": {\n" +
                " \t\t\"restaurantLocation\": {\n" +
                " \t\t\t\"latitude\": 1.0,\n" +
                " \t\t\t\"longitude\": 4.0\n" +
                " \t\t}\n" +
                " \t},\n" +
                " \t\"orderedTime\": 9.0\n" +
                " }",Order.class);

        DeliveryExec exec1 = new Gson().fromJson("{\n" +
                "\t\"id\": 6,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 9.0,\n" +
                "\t\t\"longitude\": 10.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 3.0\n" +
                "}", DeliveryExec.class);

        DeliveryExec exec2 = new Gson().fromJson("{\n" +
                "\t\"id\": 5,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 6.0,\n" +
                "\t\t\"longitude\": 8.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 5.0\n" +
                "}", DeliveryExec.class);

        DeliveryExec exec3 = new Gson().fromJson("{\n" +
                "\t\"id\": 4,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 4.0,\n" +
                "\t\t\"longitude\": 1.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 2.0\n" +
                "}", DeliveryExec.class);

        ArrayList<OrderAssignment> orderAssignmentsForGreedy = runTest(mappingForGreedy);
        ArrayList<OrderAssignment> orderAssignmentsForDp = runTest(mappingForDp);
        ArrayList<OrderAssignment> expectedAnswerForGreedy = new ArrayList<>();
        ArrayList<OrderAssignment> expectedAnswerForDp = new ArrayList<>();

        expectedAnswerForGreedy.add(new OrderAssignment(order1,exec3));
        expectedAnswerForGreedy.add(new OrderAssignment(order2,exec2));
        expectedAnswerForGreedy.add(new OrderAssignment(order3,exec1));

        expectedAnswerForDp.add(new OrderAssignment(order1,exec1));
        expectedAnswerForDp.add(new OrderAssignment(order3,exec3));
        expectedAnswerForDp.add(new OrderAssignment(order2,exec2));

        assertEquals(orderAssignmentsForGreedy,expectedAnswerForGreedy);
        assertEquals(orderAssignmentsForDp,expectedAnswerForDp);
    }

    @Test
    public void testGreedy() throws Exception {
        String mappingForGreedy = FileUtils.readFromFile("/Users/astha.a/work/TestCode/src/test/resources/attributes2.json");;

        Order order1 = new Gson().fromJson(" {\n" +
                " \t\"orderId\": 2,\n" +
                " \t\"restaurant\": {\n" +
                " \t\t\"restaurantLocation\": {\n" +
                " \t\t\t\"latitude\": 2.0,\n" +
                " \t\t\t\"longitude\": 4.0\n" +
                " \t\t}\n" +
                " \t},\n" +
                " \t\"orderedTime\": 7.0\n" +
                " }", Order.class);

        Order order2 = new Gson().fromJson(" {\n" +
                " \t\"orderId\": 1,\n" +
                " \t\"restaurant\": {\n" +
                " \t\t\"restaurantLocation\": {\n" +
                " \t\t\t\"latitude\": 2.0,\n" +
                " \t\t\t\"longitude\": 5.0\n" +
                " \t\t}\n" +
                " \t},\n" +
                " \t\"orderedTime\": 9.0\n" +
                " }", Order.class);

        Order order3 = new Gson().fromJson(" {\n" +
                " \t\"orderId\": 3,\n" +
                " \t\"restaurant\": {\n" +
                " \t\t\"restaurantLocation\": {\n" +
                " \t\t\t\"latitude\": 1.0,\n" +
                " \t\t\t\"longitude\": 4.0\n" +
                " \t\t}\n" +
                " \t},\n" +
                " \t\"orderedTime\": 9.0\n" +
                " }",Order.class);

        DeliveryExec exec1 = new Gson().fromJson("{\n" +
                "\t\"id\": 6,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 9.0,\n" +
                "\t\t\"longitude\": 10.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 3.0\n" +
                "}", DeliveryExec.class);

        DeliveryExec exec2 = new Gson().fromJson("{\n" +
                "\t\"id\": 5,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 6.0,\n" +
                "\t\t\"longitude\": 8.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 5.0\n" +
                "}", DeliveryExec.class);

        DeliveryExec exec3 = new Gson().fromJson("{\n" +
                "\t\"id\": 4,\n" +
                "\t\"currentLocation\": {\n" +
                "\t\t\"latitude\": 4.0,\n" +
                "\t\t\"longitude\": 1.0\n" +
                "\t},\n" +
                "\t\"lastOrderDeliveryTime\": 2.0\n" +
                "}", DeliveryExec.class);

        ArrayList<OrderAssignment> orderAssignmentsForGreedy = runTest(mappingForGreedy);
        ArrayList<OrderAssignment> expectedAnswerForGreedy = new ArrayList<>();

        expectedAnswerForGreedy.add(new OrderAssignment(order1,exec3));
        expectedAnswerForGreedy.add(new OrderAssignment(order2,exec2));
        expectedAnswerForGreedy.add(new OrderAssignment(order3,exec1));

        assertEquals(orderAssignmentsForGreedy,expectedAnswerForGreedy);
    }
}
