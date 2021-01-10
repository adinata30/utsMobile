package com.example.utsmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ItemDisplayActivity extends AppCompatActivity {

    private static ItemDisplayActivity ida = new ItemDisplayActivity();
    private Restaurant rest;
    private String rid;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String msg ="";
    private RecyclerView rv;
    private TextView balance_text,restaurantName;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);

        db = FirebaseFirestore.getInstance();
        account = GoogleSignIn.getLastSignedInAccount(this);

        Intent intent = getIntent();
        rid = intent.getExtras().getString("rid");
        msg = intent.getExtras().getString("Display");
        balance_text = findViewById(R.id.balance_text);
        restaurantName = findViewById(R.id.restaurant_name);

        getCurrentRestaurant();

        rv = findViewById(R.id.item_to_display_recycler_view);
        rv.setLayoutManager(new GridLayoutManager(this,2));

        getUserBalance();
        getRestaurantName();
    }

    public void gotoMyOrderPage(View view){
        changePage("My Order");
    }

    private void changePage(String toDisplay){
        Intent intent = null;
        if(toDisplay.equals("My Order")){
            intent = new Intent(this,MyOrderActivity.class);
            intent.putExtra("rid",rid);
        }
        else if(toDisplay.equals("TopUp"))
        {
            intent = new Intent(this,TopUpActivity.class);

        }
        else if(toDisplay.equals("History"))
        {
            intent = new Intent (this,HistoryActivity.class);
        }
        else {
            intent = new Intent(this,ItemDisplayActivity.class);
            intent.putExtra("rid",rid);
            intent.putExtra("Display",toDisplay);
        }
        startActivity(intent);
    }

    private void getCurrentRestaurant()
    {
        db.collection("restaurants").document(rid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    rest = task.getResult().toObject(Restaurant.class);

                    ItemDisplayAdapter adapter = null;
                    switch (msg){
                        case "Drink":
                            adapter = new ItemDisplayAdapter(new ArrayList<Item>(rest.getDrinks()) );
                            break;
                        case "Food":
                            adapter = new ItemDisplayAdapter(new ArrayList<Item>(rest.getFoods()));
                            break;
                        case "TopUp":
//                adapter = new ItemDisplayAdapter(rest.getTopUp());
                            break;
                        case "Snack":
                            adapter = new ItemDisplayAdapter(new ArrayList<Item>(rest.getSnacks()));
                            break;
                    }

                    rv.setAdapter(adapter);
                }
            }
        });
    }

    private void getRestaurantName(){
        db.collection("restaurants").document(rid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    Restaurant temp = task.getResult().toObject(Restaurant.class);
                    restaurantName.setText(temp.getName());
                }
            }
        });
    }
    private void getUserBalance() {
        db.collection("users").document(account.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    User user = task.getResult().toObject(User.class);
                    balance_text.setText(""+user.getBalance());
                }
            }
        });
    }
    public void goToHistoryPage(View view){ changePage("History");}
}