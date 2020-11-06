package com.example.utsmobile.items;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Item implements Parcelable {

    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public Item(Parcel in){
        this.name = in.readString();
        this.price = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public abstract Item getItem(String name,int price);

}
