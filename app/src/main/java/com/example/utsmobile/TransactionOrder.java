package com.example.utsmobile;

import com.example.utsmobile.adapter.OrderBase;
import com.example.utsmobile.items.Item;

public class TransactionOrder extends OrderBase {


    public TransactionOrder(Item item, int qty) {
        super(item,qty);
    }


    public TransactionOrder(){
        super();
    }

}
