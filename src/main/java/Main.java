import beans.DeliveryExec;
import beans.Location;
import beans.Order;
import beans.Restaurant;
import mappings.MyMapping;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by astha.a on 14/02/18.
 */
public class Main {
    public static void main(String[] args) {
        String attributes = "{\n" +
                "  \"DeLastOrderTime\": 1,\n" +
                "  \"OrderTime\": 1,\n" +
                "  \"MinimumDistance\": 1\n" +
                "}";

        ArrayList<Order> orders = new ArrayList<Order>();
        for(Double i = 0D; i<10D; i++){
            orders.add(new Order(new Restaurant(new Location(i+1,i+2)),i+1));
        }

        ArrayList<DeliveryExec> executives = new ArrayList<DeliveryExec>();
        int t = 0;
        for (Double i = 0D ; i < 10D ; i++ ){
            executives.add(new DeliveryExec(++t,new Location(i,i),i));
        }

        try {
            MyMapping myMapping = new MyMapping(attributes);
            HashMap<Order, DeliveryExec> mapping = myMapping.getMapping(orders, executives);
            for (Map.Entry<Order, DeliveryExec> map : mapping.entrySet()){
                System.out.println(map.getKey().getOrderedTime());
                System.out.println(map.getValue().getId());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
