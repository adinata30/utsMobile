package com.example.utsmobile.items;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Drink extends Item {
    private static ArrayList<Item> drinks;

    public Drink(String name,int price){
        super(name,price);
    }
    public static ArrayList<Item> getDrink(){
        if(drinks == null)
        {
            drinks = new ArrayList<>();
            initializeDrink();
        }
        return drinks;
    }

    public Drink(Parcel in) {
        super(in);
    }

    private static void initializeDrink(){
        drinks.add(new Drink("Air Mineral",15000));
        drinks.add(new Drink("Jus Apel",5000));
        drinks.add(new Drink("Jus Mangga",10000));
        drinks.add(new Drink("Jus Alpukat",8000));
    }
    public Item getItem(String name,int price)
    {
        Item item = null;
        int idx = drinks.indexOf(new Drink(name,price));
        if (idx!=-1)item = drinks.get(idx);
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

    public Drink() {
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel parcel) {
            return new Drink(parcel);
        }

        @Override
        public Drink[] newArray(int i) {
            return new Drink[i];
        }
    };
}
