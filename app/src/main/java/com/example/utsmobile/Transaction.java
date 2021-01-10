package com.example.utsmobile;


import com.example.utsmobile.items.Drink;

import java.util.ArrayList;
import java.util.Date;

public class Transaction  {

    private String id;
    private Restaurant restaurant;
    private ArrayList<TransactionOrder> orders;
    private long transactionDate;
    private String deliveryAddress;


    public Transaction(String id, Restaurant restaurant, ArrayList<TransactionOrder> orders, long transactionDate, String deliveryAddress) {
        this.id = id;
        this.restaurant = restaurant;
        this.orders = orders;
        this.transactionDate = transactionDate;
        this.deliveryAddress = deliveryAddress;
    }

    public Transaction() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public ArrayList<TransactionOrder> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<TransactionOrder> orders) {
        this.orders = orders;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
