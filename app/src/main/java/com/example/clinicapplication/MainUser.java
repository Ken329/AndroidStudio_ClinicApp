package com.example.clinicapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainUser extends AppCompatActivity {
    TextView name;
    ImageButton add;
    ListView list;

    String username;
    AlertDialog.Builder dialog;
    DatabaseReference ref;
    Random rant;
    int count;
    ArrayList<String> arrayservice, arraydate, arraytime, arrayperform;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        getSupportActionBar().hide();

        name = findViewById(R.id.tvUserName);
        add = findViewById(R.id.ibUserAdd);
        list = findViewById(R.id.listViewUser);

        username = getIntent().getStringExtra("username");
        name.setText(username);
        rant = new Random();
        arrayservice = new ArrayList<>();
        arraydate = new ArrayList<>();
        arraytime = new ArrayList<>();
        arrayperform = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("appointment")
                .orderByChild("client")
                .equalTo(username);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayservice.clear();
                arraydate.clear();
                arraytime.clear();
                arrayperform.clear();
                for(DataSnapshot s1 : snapshot.getChildren()){
                    arrayservice.add(s1.child("service").getValue().toString());
                    arraydate.add(s1.child("date").getValue().toString());
                    arraytime.add(s1.child("time").getValue().toString());
                    arrayperform.add(s1.child("performed").getValue().toString());
                }
                customAdapter adapter = new customAdapter();
                list.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                Toast.makeText(MainUser.this, "Successful Log Out", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPopup();
            }
        });
    }
    public void getPopup(){
        dialog = new AlertDialog.Builder(this);
        View v = getLayoutInflater().inflate(R.layout.popup_user, null);
        EditText service = (EditText)v.findViewById(R.id.etPopupUserService);
        EditText time = (EditText)v.findViewById(R.id.etPopupUserTime);
        EditText date = (EditText)v.findViewById(R.id.etPopupUserDate);
        TextView close = (TextView)v.findViewById(R.id.tvPopupUserClose);
        TextView confirm = (TextView)v.findViewById(R.id.tvPopupUserConfirm);

        dialog.setView(v);
        Dialog dialog1 = dialog.create();
        dialog1.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myService = service.getText().toString();
                String myTime = time.getText().toString();
                String myDate = date.getText().toString();
                if(arrayservice.contains(myService)){
                    Toast.makeText(MainUser.this, "Service appeared, please check", Toast.LENGTH_LONG).show();
                }else{
                    count = rant.nextInt(1000);
                    appointmentDetail appoint = new appointmentDetail(myService, myTime, myDate, "pending", username, String.valueOf(count));
                    ref = FirebaseDatabase.getInstance().getReference("appointment");
                    ref.child(String.valueOf(count)).setValue(appoint);
                    Toast.makeText(MainUser.this, "Successful made appointment", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    class customAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayservice.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.user_listview, null);
            TextView myService = (TextView) view1.findViewById(R.id.listUserService);
            TextView myDate = (TextView) view1.findViewById(R.id.listUserDate);
            TextView myTime = (TextView) view1.findViewById(R.id.listUserTime);
            TextView myPerform = (TextView) view1.findViewById(R.id.listUserPerform);

            myService.setText(arrayservice.get(position));
            myDate.setText(arraydate.get(position));
            myTime.setText(arraytime.get(position));
            myPerform.setText(arrayperform.get(position));
            return view1;
        }
    }
}