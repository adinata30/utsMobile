package com.example.utsmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utsmobile.items.Item;

import java.util.ArrayList;

public class DoOrderActivity extends AppCompatActivity {

    TextView nameView,priceView;
    EditText qtyEdit;
    Item currentItem;
    Order currentOrder =  null;
    boolean newOrder = false;
    private Button orderBtn;
    private String rid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_order);

        Intent intent = getIntent();
        currentItem = intent.getParcelableExtra("Item");
        rid = FindRestaurant.rid;
        String name = currentItem.getName();
        Integer price = currentItem.getPrice();
        orderBtn = findViewById(R.id.order_more_btn);

        if(currentItem.getStock() <=0)
        {
            orderBtn.setEnabled(false);
            orderBtn.setText("Out of Stock, cannot order");
        }
//        Toast.makeText(this,""+Order.getInstance().getOrders().size(),Toast.LENGTH_SHORT).show();
        currentOrder = Order.getOrder(currentItem);
        qtyEdit = findViewById(R.id.current_qty);
        if(currentOrder == null)
        {
            newOrder = true;
            qtyEdit.setText("0");
        }
        else qtyEdit.setText(""+currentOrder.getQty());


        nameView = findViewById(R.id.current_name);
        nameView.setText(name);
        priceView = findViewById(R.id.current_price);
        priceView.setText(""+price);

    }
    public void goBack(View view)
    {
        int idx = 0;
        if (newOrder)
        {

            Order.addOrder(currentItem,Integer.parseInt(qtyEdit.getText().toString()));
            idx = Order.getInstance().getOrders().size()-1;
        }
        else {
            idx = Order.getInstance().getOrders().indexOf(Order.getOrder(currentItem));
//            Toast.makeText(this,""+(Integer.parseInt(qtyEdit.getText().toString())),Toast.LENGTH_SHORT).show();
            currentOrder.setQty(Integer.parseInt(qtyEdit.getText().toString()));
            Order.getInstance().getOrders().set(idx,currentOrder);
        }
        if(Integer.parseInt(qtyEdit.getText().toString()) == 0)Order.removeFromOrder(idx);
        finish();
    }
    public void gotoMyOrderPage(View view){
        if(currentItem.getStock() <=0)
        {
            changePage("My Order");
            return;
        }
        int idx = 0;
        if (newOrder)
        {

            Order.addOrder(currentItem,Integer.parseInt(qtyEdit.getText().toString()));
            idx = Order.getInstance().getOrders().size()-1;
        }
        else {
            idx = Order.getInstance().getOrders().indexOf(Order.getOrder(currentItem));
//            Toast.makeText(this,""+(Integer.parseInt(qtyEdit.getText().toString())),Toast.LENGTH_SHORT).show();
            currentOrder.setQty(Integer.parseInt(qtyEdit.getText().toString()));
            Order.getInstance().getOrders().set(idx,currentOrder);
        }
        if(Integer.parseInt(qtyEdit.getText().toString()) == 0)Order.removeFromOrder(idx);
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