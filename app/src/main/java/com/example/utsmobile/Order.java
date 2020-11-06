package com.example.utsmobile;

import com.example.utsmobile.items.Item;

import java.util.ArrayList;

public class Order {

    private Item item;
    private int qty;

    private Order(Item item, int qty) {
        this.item = item;
        this.qty = qty;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
    private static ArrayList<Order> orders = new ArrayList<>();
    private static Order order = new Order();
    private Order(){}
    public ArrayList<Order>getOrders(){
        return orders;
    }
    public static Order getInstance()
    {
        return order;
    }
    public static void addOrder(Item item, int qty)
    {
        orders.add(new Order(item,qty));
    }
    public static Order getOrder(Item item)
    {
        int idx=0;
        for (Order temp:orders)
        {
//            if(temp.item.getName().equals(name) && temp.item.getPrice() == price)return orders.get(idx);
            if(temp.item.getPrice() == item.getPrice() && temp.item.getName().equals(item.getName()))return orders.get(idx);
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
