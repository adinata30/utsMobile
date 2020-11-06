
package com.example.utsmobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utsmobile.adapter.MyOrderAdapter;

public class FinishOrderActivity extends AppCompatActivity {

    MyOrderAdapter moa = null;
    TextView totalView = null;
    int priceTotal = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order);

        Intent intent = getIntent();
        priceTotal = Integer.parseInt(intent.getExtras().getString("Total"));

        RecyclerView rv = findViewById(R.id.order_recyler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        moa = new MyOrderAdapter(Order.getInstance().getOrders());
        moa.changeToFinishOrder();
        rv.setAdapter(moa);
        totalView = findViewById(R.id.total_price);
        totalView.setText("Rp. "+priceTotal);
    }

    public void refreshApp(View view){


        Intent intent = new Intent (this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.finish();

    }
}