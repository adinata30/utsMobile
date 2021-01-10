package com.example.utsmobile;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.utsmobile.items.Food;

public class User implements Parcelable {
    private String id;
    private String name;
    private String email;
    private int balance;

    public User() {
    }

    public User(String id, String name, String email, int balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeInt(balance);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            User user = new User();
            user.id = parcel.readString();
            user.name = parcel.readString();
            user.email = parcel.readString();
            user.balance = parcel.readInt();
            return user;

        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
