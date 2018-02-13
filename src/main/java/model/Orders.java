package model;

import beans.Order;

import java.util.ArrayList;

/**
 * Created by astha.a on 12/02/18.
 */


//Singleton class
public class Orders {
    private static Integer MAXIMUM_NUMBER_OF_ORDERS = 100;
    private ArrayList<Order> pQueue;

    private static Orders instance;

    private Orders() {
        pQueue = new ArrayList<Order>();
    }

    public static Orders getInstance() {
        if (instance == null) {
            synchronized (Orders.class) {
                if (instance == null) {
                    instance = new Orders();
                }
            }
        }

        return instance;
    }

    public void addNewOrder(Order order) {
        pQueue.add(order);
    }

    public void removeOldOrder(Order order){
        pQueue.remove(order);
    }

}
