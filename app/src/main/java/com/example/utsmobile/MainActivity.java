package com.example.utsmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Order.refresh();
    }

    public void gotoMyOrderPage(View view){
        changePage("My Order");
    }
    public void gotoDrinkPage(View view){
        changePage("Drink");
    }
    public void gotoFoodPage(View view){
        changePage("Food");
    }
    public void gotoSnackPage(View view){
        changePage("Snack");
    }
    public void gotoTopUpPage(View view){
        changePage("TopUp");
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