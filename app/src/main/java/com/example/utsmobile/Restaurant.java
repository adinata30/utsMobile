package com.example.utsmobile;

import com.example.utsmobile.items.Drink;
import com.example.utsmobile.items.Food;
import com.example.utsmobile.items.Item;
import com.example.utsmobile.items.Snack;

import java.util.ArrayList;

public class Restaurant {
    private String id,name;
    private ArrayList<Food> foods;
    private ArrayList<Drink> drinks;
    private ArrayList<Snack> snacks;
    private double latitude;
    private double longitude;

    public Restaurant() {
    }

    public Restaurant(String id, String name, ArrayList<Food> foods, ArrayList<Drink> drinks, ArrayList<Snack> snacks, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.foods = foods;
        this.drinks = drinks;
        this.snacks = snacks;
        this.latitude = latitude;
        this.longitude = longitude;
    }

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

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }

    public ArrayList<Snack> getSnacks() {
        return snacks;
    }

    public void setSnacks(ArrayList<Snack> snacks) {
        this.snacks = snacks;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
