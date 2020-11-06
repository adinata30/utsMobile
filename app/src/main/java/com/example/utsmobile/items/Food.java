package com.example.utsmobile.items;

import android.annotation.SuppressLint;
import android.os.Parcel;

import java.util.ArrayList;


public class Food extends Item {

    private static ArrayList<Item> foods;

    public Food(String name,int price){
        super(name,price);

    }
    public static ArrayList<Item> getFood(){
        if(foods == null)
        {
            foods = new ArrayList<>();
            initializeFood();
        }
        return foods;
    }

    private static void initializeFood(){
        foods.add(new Food("Ayam Goreng",15000));
        foods.add(new Food("Kentang Goreng",5000));
        foods.add(new Food("Bakso Goreng",10000));
        foods.add(new Food("Jamur Crispy",8000));
    }
    public Item getItem(String name,int price)
    {
        Item item = null;
        int idx = foods.indexOf(new Food(name,price));
        if (idx!=-1)item = foods.get(idx);
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
    }

    public Food(Parcel in) {
        super(in);
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel parcel) {
            return new Food(parcel);
        }

        @Override
        public Food[] newArray(int i) {
            return new Food[i];
        }
    };
}
