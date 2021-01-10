package com.example.utsmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utsmobile.adapter.MyOrderAdapter;
import com.example.utsmobile.adapter.OrderBase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyOrderActivity extends AppCompatActivity {

    int priceTotal=0;
    MyOrderAdapter moa = null;
    TextView totalView = null;
    private FirebaseFirestore db;
    private GoogleSignInAccount account;
    private User user;
    private String rid = "";
    private Restaurant restaurant;
    ArrayList<TransactionOrder> currentOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        currentOrder = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        account = GoogleSignIn.getLastSignedInAccount(this);
        Intent intent = getIntent();
        rid = FindRestaurant.rid;
        getCurrentRestaurant();
        getCurrentUser();
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
        for (OrderBase temp: Order.getInstance().getOrders())
        {
            priceTotal += temp.getItem().getPrice()*temp.getQty();
            TransactionOrder t = new TransactionOrder();
            t.setQty(temp.getQty());
            t.setItem(temp.getItem());
            currentOrder.add(t);
        }
        totalView.setText("Rp. "+priceTotal);
    }
    private void getCurrentRestaurant()
    {
        db.collection("restaurants").document(rid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    restaurant = task.getResult().toObject(Restaurant.class);
                }
            }
        });
    }

    private void getCurrentUser(){
        db.collection("users").document(account.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful())
            {
                user = task.getResult().toObject(User.class);
            }
            }
        });
    }
    public void finishOrder(View view) throws IOException {
        if(priceTotal ==0){
            Toast.makeText(this,"Please atleast order 1 item",Toast.LENGTH_SHORT).show();
            return;
        }
        if(priceTotal > user.getBalance())
        {
            Toast.makeText(this,"You don't have enough balance, please top up first.",Toast.LENGTH_SHORT).show();
            return;
        }

        //substract balance
        final DocumentReference userRef = db.collection("users").document(account.getId());
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful())
            {
                User u = task.getResult().toObject(User.class);
                u.setBalance(u.getBalance() - priceTotal);
                userRef.set(u);
            }
            }
        });

        //insert transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(Calendar.getInstance().getTime().getTime());
        transaction.setOrders(currentOrder);
        transaction.setRestaurant(restaurant);
        Geocoder coder = new Geocoder(this);
        LatLng coord = FindRestaurant.coord;
        List<Address>  addrs= coder.getFromLocation(coord.latitude,coord.longitude,1);
        Address addr = (Address) addrs.toArray()[0];
        transaction.setDeliveryAddress(addr.getThoroughfare()+" No "+addr.getSubThoroughfare());
        DocumentReference dref = db.collection("users").document(user.getId()).collection("transactions").document();
        transaction.setId(dref.getId());
        dref.set(transaction);



        Intent intent = new Intent(this,FinishOrderActivity.class);
        intent.putExtra("Total",""+priceTotal);
        startActivity(intent);
    }
    public int calculateTotalPrice(){
        int res = 0;
        for (OrderBase temp: Order.getInstance().getOrders())
        {
            res += temp.getItem().getPrice()*temp.getQty();
        }
        return res;
    }
}