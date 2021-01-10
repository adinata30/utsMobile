package com.example.utsmobile.adapter;

import com.example.utsmobile.items.Item;

public class OrderBase {
    private Item item;
    private int qty;

    public OrderBase(Item item, int qty) {
        this.item = item;
        this.qty = qty;
    }

    public OrderBase() {
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
}
