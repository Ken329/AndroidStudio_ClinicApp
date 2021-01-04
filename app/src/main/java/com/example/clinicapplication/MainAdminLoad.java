package com.example.clinicapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

public class MainAdminLoad extends AppCompatActivity {
    ImageButton back;
    ListView list;

    ArrayList<String> service, date, time, perform, code, client;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_load);
        getSupportActionBar().hide();

        back = findViewById(R.id.ibAdminLoadBack);
        list = findViewById(R.id.listViewAdminLoad);

        service = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();
        perform = new ArrayList<>();
        code = new ArrayList<>();
        client = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("appointment");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                service.clear();
                date.clear();
                time.clear();
                perform.clear();
                code.clear();
                for (DataSnapshot s1 : snapshot.getChildren()){
                    service.add(s1.child("service").getValue().toString());
                    date.add(s1.child("date").getValue().toString());
                    time.add(s1.child("time").getValue().toString());
                    perform.add(s1.child("performed").getValue().toString());
                    code.add((s1.child("code").getValue().toString()));
                    client.add((s1.child("client").getValue().toString()));
                }
                customAdapter adapter = new customAdapter();
                list.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popUp(code.get(position), client.get(position), perform.get(position));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(v.getContext(), MainAdmin.class);
                startActivity(intent);
            }
        });
    }
    public void popUp(String myCode, String myClient, String perform){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        if(perform.equals("pending")){
            View view1 = getLayoutInflater().inflate(R.layout.employee_approval, null);
            EditText name = view1.findViewById(R.id.etPopupApproveName);
            TextView enter = view1.findViewById(R.id.etPopupApproveEnter);

            dialog.setView(view1);
            Dialog dialog1 = dialog.create();
            dialog1.show();
            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String myName = name.getText().toString();
                    Query query = FirebaseDatabase.getInstance().getReference("clinic").child("employee")
                            .orderByChild("employee_name")
                            .equalTo(myName);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                ref = FirebaseDatabase.getInstance().getReference("appointment").child(myCode);
                                ref.child("performed").setValue(myName);
                                dialog1.dismiss();
                                Toast.makeText(MainAdminLoad.this, "Successful approved", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(MainAdminLoad.this, "Invalid employee name, please check", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            });
        }else{
            View view1 = getLayoutInflater().inflate(R.layout.activity_main_sign_up, null);
            EditText name = view1.findViewById(R.id.etSignUpName);
            EditText password = view1.findViewById(R.id.etSignUpPassword);
            EditText phone = view1.findViewById(R.id.etSignUpPhone);
            EditText age = view1.findViewById(R.id.etSignUpAge);
            LinearLayout close = view1.findViewById(R.id.linearLayout1);
            LinearLayout enter = view1.findViewById(R.id.linearLayouot2);
            close.setVisibility(View.INVISIBLE);
            enter.setVisibility(View.INVISIBLE);
            password.setVisibility(View.INVISIBLE);
            name.setEnabled(false);
            phone.setEnabled(false);
            age.setEnabled(false);
            ref = FirebaseDatabase.getInstance().getReference("user").child(myClient);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String myPhone = snapshot.child("phoneNumber").getValue().toString();
                    String myAge = snapshot.child("age").getValue().toString();
                    name.setText(myClient);
                    age.setText(myAge);
                    phone.setText(myPhone);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            dialog.setView(view1);
            Dialog dialog1 = dialog.create();
            dialog1.show();
        }
    }
    class customAdapter extends BaseAdapter {
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