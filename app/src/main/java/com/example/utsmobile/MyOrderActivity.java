package com.example.utsmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utsmobile.adapter.MyOrderAdapter;

public class MyOrderActivity extends AppCompatActivity {

    int priceTotal=0;
    MyOrderAdapter moa = null;
    TextView totalView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        RecyclerView rv = findViewById(R.id.order_recyler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        moa = new MyOrderAdapter(Order.getInstance().getOrders());
        moa.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                priceTotal = calculateTotalPrice();
                totalView.setText("Rp. "+priceTotal);
            }
        });
        rv.setAdapter(moa);
        totalView = findViewById(R.id.total_price);
        priceTotal = 0;
        for (Order temp: Order.getInstance().getOrders())
        {
            priceTotal += temp.getItem().getPrice()*temp.getQty();
        }
        totalView.setText("Rp. "+priceTotal);
    }

    public void finishOrder(View view)
    {
        if(priceTotal ==0){
            Toast.makeText(this,"Please atleast order 1 item",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this,FinishOrderActivity.class);
        intent.putExtra("Total",""+priceTotal);
        startActivity(intent);
    }
    public int calculateTotalPrice(){
        int res = 0;
        for (Order temp: Order.getInstance().getOrders())
        {
            res += temp.getItem().getPrice()*temp.getQty();
        }
        return res;
    }
}