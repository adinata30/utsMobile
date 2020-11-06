package com.example.utsmobile.items;

import android.annotation.SuppressLint;
import android.os.Parcel;

import java.util.ArrayList;

@SuppressLint("ParcelCreator")
public class TopUp extends Item {
    private static ArrayList<Item> topUps;

    public TopUp(String name,int price){
        super(name,price);
    }
    public static ArrayList<Item> getTopUp(){
        if(topUps == null)
        {
            topUps = new ArrayList<>();
            initializeTopUp();
        }
        return topUps;
    }

    private static void initializeTopUp(){
        topUps.add(new TopUp("Saus Sambal",500));
        topUps.add(new TopUp("Es Batu",500));
        topUps.add(new TopUp("Saus Tomat",500));
        topUps.add(new TopUp("Mayonnaise",1000));
    }
    public Item getItem(String name,int price)
    {
        Item item = null;
        int idx = topUps.indexOf(new TopUp(name,price));
        if (idx!=-1)item = topUps.get(idx);
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

    public TopUp(Parcel in) {
        super(in);
    }
    public static final Creator<TopUp> CREATOR = new Creator<TopUp>() {
        @Override
        public TopUp createFromParcel(Parcel parcel) {
            return new TopUp(parcel);
        }

        @Override
        public TopUp[] newArray(int i) {
            return new TopUp[i];
        }
    };
}
