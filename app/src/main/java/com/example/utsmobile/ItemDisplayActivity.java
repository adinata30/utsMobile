package com.example.utsmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utsmobile.adapter.ItemDisplayAdapter;
import com.example.utsmobile.items.Drink;
import com.example.utsmobile.items.Food;
import com.example.utsmobile.items.Item;
import com.example.utsmobile.items.Snack;
import com.example.utsmobile.items.TopUp;

import java.util.ArrayList;

public class ItemDisplayActivity extends AppCompatActivity {

    private static ItemDisplayActivity ida = new ItemDisplayActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);
        Intent intent = getIntent();
        String msg = intent.getExtras().getString("Display");
        RecyclerView rv = findViewById(R.id.item_to_display_recycler_view);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        ItemDisplayAdapter adapter = null;
        switch (msg){
            case "Drink":
                adapter = new ItemDisplayAdapter(Drink.getDrink());
                break;
            case "Food":
                adapter = new ItemDisplayAdapter(Food.getFood());
                break;
            case "TopUp":
                adapter = new ItemDisplayAdapter(TopUp.getTopUp());
                break;
            case "Snack":
                adapter = new ItemDisplayAdapter(Snack.getSnack());
                break;
        }

        rv.setAdapter(adapter);


    }

    public void gotoMyOrderPage(View view){
        changePage("My Order");
    }

    private void changePage(String toDisplay){
        Intent intent;
        if(!toDisplay.equals("My Order")){
            intent = new Intent(this,ItemDisplayActivity.class);
            intent.putExtra("Display",toDisplay);
        }
        else intent = new Intent(this,MyOrderActivity.class);
        startActivity(intent);
    }


}