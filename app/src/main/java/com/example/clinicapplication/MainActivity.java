package com.example.clinicapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextInputEditText username, password;
    TextView signUp, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        username = findViewById(R.id.tvdtLoginUsername);
        password = findViewById(R.id.tvdtLoginPassword);
        signUp = findViewById(R.id.tvdtLoginSignUp);
        login = findViewById(R.id.tvLoginEnter);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainSignUp.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myUsername = username.getText().toString();
                String myPassword = password.getText().toString();
                if(myUsername.equals("admin")){
                    if(myPassword.equals("admin")){
                        Toast.makeText(MainActivity.this, "Successful Login Admin", Toast.LENGTH_SHORT).show();
                    }else{
                        password.setError("Wrong admin password");
                    }
                }else{
                    Query query = FirebaseDatabase.getInstance().getReference("user")
                            .orderByChild("username")
                            .equalTo(myUsername);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String dataPass = snapshot.child(myUsername).child("password").getValue().toString();
                                if(dataPass.equals(myPassword)){
                                    Toast.makeText(MainActivity.this, "Welcome back " + myUsername, Toast.LENGTH_SHORT).show();
                                }else{
                                    password.setError("Wrong password");
                                }
                            }else{
                                username.setError("Invalid username");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}