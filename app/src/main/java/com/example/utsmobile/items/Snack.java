package com.example.utsmobile.items;

import android.annotation.SuppressLint;
import android.os.Parcel;

import java.util.ArrayList;


public class Snack extends Item {
    private static ArrayList<Item> snacks;

    public Snack(String name,int price){
        super(name,price);
    }
    public static ArrayList<Item> getSnack(){
        if(snacks == null)
        {
            snacks = new ArrayList<>();
            initializeSnack();
        }
        return snacks;
    }

    public Snack() {
    }

    private static void initializeSnack(){
        snacks.add(new Snack("Snickers",15000));
        snacks.add(new Snack("Chitato",5000));
        snacks.add(new Snack("Mr. Potato",10000));
        snacks.add(new Snack("Oreo",8000));
    }
    public Item getItem(String name,int price)
    {
        Item item = null;
        int idx = snacks.indexOf(new Snack(name,price));
        if (idx!=-1)item = snacks.get(idx);
        return item;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getName());
        parcel.writeInt(this.getPrice());
        parcel.writeInt(this.getStock());
    }

    public Snack(Parcel in) {
        super(in);
    }
    public static final Creator<Snack> CREATOR = new Creator<Snack>() {
        @Override
        public Snack createFromParcel(Parcel parcel) {
            return new Snack(parcel);
        }

        @Override
        public Snack[] newArray(int i) {
            return new Snack[i];
        }
    };
}
