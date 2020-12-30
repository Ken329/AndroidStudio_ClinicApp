package com.example.clinicapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextInputEditText username, password;
    TextView signUp, login;
    ImageView logo;

    String address, phone, payment, hour, day;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        logo = findViewById(R.id.ivMain);
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
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = FirebaseDatabase.getInstance().getReference("clinic");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        address = snapshot.child("clinic_detail").child("address").getValue().toString();
                        phone = snapshot.child("clinic_detail").child("phone").getValue().toString();
                        payment = snapshot.child("clinic_detail").child("payment").getValue().toString();
                        hour = snapshot.child("clinic_detail").child("working_hours").getValue().toString();
                        day = snapshot.child("clinic_detail").child("working_days").getValue().toString();
                        getPopUp();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myUsername = username.getText().toString();
                String myPassword = password.getText().toString();
                if(myUsername.equals("admin")){
                    if(myPassword.equals("admin")){
                        Intent intent = new Intent(v.getContext(), MainAdmin.class);
                        Toast.makeText(MainActivity.this, "Successful Login Admin", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
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
    public void getPopUp(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View popUp = getLayoutInflater().inflate(R.layout.popup, null);
        TextView tvAddress =(TextView) popUp.findViewById(R.id.tvPopupAddress);
        TextView tvPhone =(TextView) popUp.findViewById(R.id.tvPopupPhone);
        TextView tvPayment =(TextView) popUp.findViewById(R.id.tvPopupPayment);
        TextView tvHour =(TextView) popUp.findViewById(R.id.tvPopupHour);
        TextView tvDay =(TextView) popUp.findViewById(R.id.tvPopupDay);
        TextView tvClose = (TextView)popUp.findViewById(R.id.tvPopupClose);

        tvAddress.setText(address);
        tvPhone.setText(phone);
        tvPayment.setText(payment);
        tvHour.setText(hour);
        tvDay.setText(day);

        dialog.setView(popUp);
        Dialog dialog1 = dialog.create();
        dialog1.show();
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }
}