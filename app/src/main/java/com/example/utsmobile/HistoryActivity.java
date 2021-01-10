package com.example.utsmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.utsmobile.adapter.TransactionHistoryAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<Transaction> transactions;
    private GoogleSignInAccount account;
    private FirebaseFirestore db;
    private RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        transactions = new ArrayList<>();
        account = GoogleSignIn.getLastSignedInAccount(this);
        db = FirebaseFirestore.getInstance();
        rv = findViewById(R.id.transaction_history_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));

        getTransactions();
    }
    private void getTransactions(){
        db.collection("users").document(account.getId()).collection("transactions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    transactions = (ArrayList<Transaction>) task.getResult().toObjects(Transaction.class);
                    TransactionHistoryAdapter tha = new TransactionHistoryAdapter(transactions);
                    rv.setAdapter(tha);
                }
            }
        });
    }
}