package com.example.utsmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TopUpActivity extends AppCompatActivity {

    private String uid;
    private GoogleSignInAccount account ;
    private User user;
    private FirebaseFirestore db;
    private TextView userBalance;
    private EditText topUp;
    private Button topUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        account = GoogleSignIn.getLastSignedInAccount(this);
        db = FirebaseFirestore.getInstance();
        userBalance = findViewById(R.id.current_balace_text);
        topUp = findViewById(R.id.top_up_amount);
        topUpBtn = findViewById(R.id.top_up_btn);
        getUserBalance();

        topUp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Integer.parseInt(topUp.getText().toString()) > 2000000)
                {
                    topUpBtn.setEnabled(false);
                }
                else topUpBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        topUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topUp(Integer.parseInt(topUp.getText().toString()));
                finish();
            }
        });
    }

    private void getUserBalance()
    {
        db.collection("users").document(account.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    user = task.getResult().toObject(User.class);
                    userBalance.setText(""+user.getBalance());
                }
            }
        });
    }
    private void topUp(int amount)
    {
        user.setBalance(user.getBalance() + amount);
        db.collection("users").document(account.getId()).set(user);
    }
}