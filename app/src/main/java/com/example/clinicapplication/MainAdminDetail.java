package com.example.clinicapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainAdminDetail extends AppCompatActivity {
    ImageButton back;
    TextView update;
    EditText address, phone, hour;
    RadioGroup days, payment;
    RadioButton getday, getPayment;

    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_detail);
        getSupportActionBar().hide();

        back = findViewById(R.id.ibAdminDetailBack);
        update = findViewById(R.id.tvAdminDetailUpdate);
        address = findViewById(R.id.etAdminDetailAddress);
        phone = findViewById(R.id.etAdminDetailPhone);
        hour = findViewById(R.id.etAdminDetailHour);
        days = findViewById(R.id.rgAdminDetailDay);
        payment = findViewById(R.id.rgAdminDetailPayment);
        getday = findViewById(R.id.rb1);

        ref = FirebaseDatabase.getInstance().getReference("clinic").child("clinic_detail");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myAddress = snapshot.child("address").getValue().toString();
                String myPhone = snapshot.child("phone").getValue().toString();
                String myHour = snapshot.child("working_hours").getValue().toString();
                String myday = snapshot.child("working_days").getValue().toString();
                String myPay = snapshot.child("payment").getValue().toString();

                address.setText(myAddress);
                phone.setText(myPhone);
                hour.setText(myHour);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(v.getContext(), MainAdmin.class);
                startActivity(intent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getButton();
                String myAddress = address.getText().toString();
                String myPhone = phone.getText().toString();
                String myHour = hour.getText().toString();
                String myday = getday.getText().toString();
                String myPay = getPayment.getText().toString();

                clinicDetail clinic = new clinicDetail(myAddress, myPhone, myHour, myday, myPay);
                ref = FirebaseDatabase.getInstance().getReference("clinic").child("clinic_detail");
                ref.setValue(clinic);
                Toast.makeText(MainAdminDetail.this, "Successful update", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getCheck(){

    }
    public void getButton(){
        int myday = days.getCheckedRadioButtonId();
        int myPay = payment.getCheckedRadioButtonId();

        getday = findViewById(myday);
        getPayment = findViewById(myPay);
    }
}