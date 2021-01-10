package com.example.utsmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private String rid;
    private FirebaseFirestore db;
    private TextView balance_text,restaurantName;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        balance_text = findViewById(R.id.balance_text);
        restaurantName = findViewById(R.id.restaurant_name);
        Intent intent = getIntent();
        rid = intent.getExtras().getString("rid");

        db = FirebaseFirestore.getInstance();

        account = GoogleSignIn.getLastSignedInAccount(this);

        getUserBalance();
        getRestaurantName();
        Order.refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserBalance();
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
    public void goToHistoryPage(View view){ changePage("History");}

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


}