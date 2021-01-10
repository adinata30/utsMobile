
package com.example.utsmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utsmobile.adapter.MyOrderAdapter;
import com.example.utsmobile.adapter.OrderBase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FinishOrderActivity extends AppCompatActivity {

    private final AppCompatActivity CURRENT_ACTIVITY = this;
    MyOrderAdapter moa = null;
    TextView totalView = null;
    int priceTotal = 0;
    String tid = "";
    private FirebaseFirestore db;
    private TextView completeOrderMsg;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_order);
        db = FirebaseFirestore.getInstance();
        account = GoogleSignIn.getLastSignedInAccount(this);
        Intent intent = getIntent();
        completeOrderMsg = findViewById(R.id.complete_order_text);
        String total = intent.getExtras().getString("Total");
        try{
            priceTotal = Integer.parseInt(total);
        }catch (Exception e)
        {
            priceTotal = 0;
        }
        tid = intent.getExtras().getString("tid");
        if(tid != null)
        {
            final ArrayList<OrderBase> order = new ArrayList<>();
            db.collection("users").document(account.getId()).collection("transactions").document(tid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if(task.isSuccessful())
                    {
                        Transaction temp = task.getResult().toObject(Transaction.class);
                        completeOrderMsg.setText("Order Details for Transaction : "+temp.getId());
                        for(TransactionOrder to : temp.getOrders())
                        {
                            order.add((OrderBase) to);
                            priceTotal += to.getItem().getPrice() * to.getQty();
                        }
                        moa = new MyOrderAdapter(order);
                        RecyclerView rv = findViewById(R.id.order_recyler_view);
                        rv.setLayoutManager(new LinearLayoutManager(CURRENT_ACTIVITY));

                        moa.changeToFinishOrder();
                        rv.setAdapter(moa);
                        totalView = findViewById(R.id.total_price);
                        totalView.setText("Rp. "+priceTotal);
                    }
                }
            });

        }
        else {
            moa = new MyOrderAdapter(Order.getInstance().getOrders());
            RecyclerView rv = findViewById(R.id.order_recyler_view);
            rv.setLayoutManager(new LinearLayoutManager(this));

            moa.changeToFinishOrder();
            rv.setAdapter(moa);
            totalView = findViewById(R.id.total_price);
            totalView.setText("Rp. "+priceTotal);
        }

    }

    public void refreshApp(View view){


        Intent intent = new Intent (this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        this.finish();

    }

}