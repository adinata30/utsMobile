package com.example.utsmobile.items;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    public Item() {
    }

    private String name;
    private int price;
    private int stock;
    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Item(Parcel in){
        this.name = in.readString();
        this.price = in.readInt();
        this.stock = in.readInt();
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

    public Item getItem(String name,int price){
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeInt(stock);
    }
}
