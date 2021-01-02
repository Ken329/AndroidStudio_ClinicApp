package com.example.clinicapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainUser extends AppCompatActivity {
    TextView name;
    ImageButton add;
    ListView list;

    String username;
    AlertDialog.Builder dialog;
    DatabaseReference ref;
    Boolean check = true;
    ArrayList<String> service, date, time, perform;
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

        service = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();
        perform = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("appointment").child(username);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                service.clear();
                date.clear();
                time.clear();
                perform.clear();
                for(DataSnapshot s1 : snapshot.getChildren()){
                    service.add(s1.child("service").getValue().toString());
                    date.add(s1.child("date").getValue().toString());
                    time.add(s1.child("time").getValue().toString());
                    perform.add(s1.child("performed").getValue().toString());
                }
                customAdapter adapter = new customAdapter();
                list.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
                Query query = FirebaseDatabase.getInstance().getReference("appointment").child(username)
                        .orderByChild("service")
                        .equalTo(myService);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(MainUser.this, "Appointment appeared, please check", Toast.LENGTH_LONG).show();
                        }else{
                            appointmentDetail appoint = new appointmentDetail(myService, myTime, myDate, "pending");
                            ref = FirebaseDatabase.getInstance().getReference("appointment");
                            ref.child(username).child(myService).setValue(appoint);
                            Toast.makeText(MainUser.this, "Successful made appointment", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
    }
    class customAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return service.size();
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

            myService.setText(service.get(position));
            myDate.setText(date.get(position));
            myTime.setText(time.get(position));
            myPerform.setText(perform.get(position));
            return view1;
        }
    }
}