package com.example.clinicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainAdmin extends AppCompatActivity {
    ImageButton back;
    TextView detail, load, employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        getSupportActionBar().hide();

        back = findViewById(R.id.ibAdminBack);
        detail = findViewById(R.id.tvAdminDetail);
        load = findViewById(R.id.tvAdminLoad);
        employee = findViewById(R.id.tvAdminEmployee);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAct(v, "back");
            }
        });
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAct(v, "detail");
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAct(v, "load");
            }
        });
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAct(v, "employee");
            }
        });
    }
    public void goAct(View v, String act){
        Intent intent;
        switch (act){
            case "back":
                intent = new Intent(v.getContext(), MainActivity.class);
                break;
            case "detail":
                intent = new Intent(v.getContext(), MainAdminDetail.class);
                break;
            case "load":
                intent = new Intent(v.getContext(), MainAdminLoad.class);
                break;
            case "employee":
                intent = new Intent(v.getContext(), MainAdminEmployee.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + act);
        }
        startActivity(intent);
    }
}