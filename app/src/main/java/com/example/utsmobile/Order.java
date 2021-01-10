package com.example.utsmobile;

import com.example.utsmobile.adapter.OrderBase;
import com.example.utsmobile.items.Item;

import java.util.ArrayList;

public class Order extends OrderBase {



    private Order(Item item, int qty) {
        super(item,qty);
    }


    private static ArrayList<OrderBase> orders = new ArrayList<>();
    private static Order order = new Order();

    public ArrayList<OrderBase>getOrders(){
        return orders;
    }
    public static Order getInstance()
    {
        return order;
    }
    public Order()
    {
        super();
    }
    public static void addOrder(Item item, int qty)
    {
        orders.add(new Order(item,qty));
    }
    public static Order getOrder(Item item)
    {
        int idx=0;
        for (OrderBase temp:orders)
        {
//            if(temp.item.getName().equals(name) && temp.item.getPrice() == price)return orders.get(idx);
            if(temp.getItem().getPrice() == item.getPrice() && temp.getItem().getName().equals(item.getName()) && temp.getItem().getStock() == item.getStock())return (Order)orders.get(idx);
            idx++;
        }
        return null;
    }

    public static void refresh(){
        orders = new ArrayList<>();
    }
    public static void removeFromOrder(int idx)
    {
        orders.remove(idx);
    }


}
