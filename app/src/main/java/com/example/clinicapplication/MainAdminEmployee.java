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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainAdminEmployee extends AppCompatActivity {
    ImageButton back, add;
    ListView list;
    AlertDialog.Builder dialog;
    DatabaseReference ref;
    ArrayList<String> arrayName, arrayAge, arrayPhone, arrayRole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin_employee);
        getSupportActionBar().hide();

        back = findViewById(R.id.ibAdminEmployeeBack);
        add = findViewById(R.id.ibAdminEmployeeAdd);
        list = findViewById(R.id.ibAdminEmployeeListView);

        arrayName = new ArrayList<>();
        arrayAge = new ArrayList<>();
        arrayPhone = new ArrayList<>();
        arrayRole = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("clinic").child("employee");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayName.clear();
                arrayAge.clear();
                arrayPhone.clear();
                arrayRole.clear();
                for(DataSnapshot s1 : snapshot.getChildren()){
                    arrayName.add(s1.child("employee_name").getValue().toString());
                    arrayAge.add(s1.child("employee_age").getValue().toString());
                    arrayPhone.add(s1.child("employee_phone").getValue().toString());
                    arrayRole.add(s1.child("employee_role").getValue().toString());
                }
                customAdapter adapter = new customAdapter();
                list.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                getEmployee(arrayName.get(position));
                return false;
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainAdmin.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEmployee("add");
            }
        });
    }
    public void getEmployee(String act){
        dialog = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup_employee, null);
        EditText name = (EditText)view.findViewById(R.id.etPopupEmployeeName);
        EditText age = (EditText)view.findViewById(R.id.etPopupEmployeeAge);
        EditText phone = (EditText)view.findViewById(R.id.etPopupEmployeePhone);
        EditText role = (EditText)view.findViewById(R.id.etPopupEmployeeRole);
        TextView close = (TextView)view.findViewById(R.id.tvPopupEmplyeeClose);
        TextView add = (TextView)view.findViewById(R.id.tvPopupEmplyeeAdd);
        TextView title =(TextView)view.findViewById(R.id.textView7);
        if(act != "add"){
            add.setText("Update");
            title.setText("Update Employee");
            name.setEnabled(false);
            ref = FirebaseDatabase.getInstance().getReference("clinic").child("employee").child(act);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    name.setText(snapshot.child("employee_name").getValue().toString());
                    age.setText(snapshot.child("employee_age").getValue().toString());
                    phone.setText(snapshot.child("employee_phone").getValue().toString());
                    role.setText(snapshot.child("employee_role").getValue().toString());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        dialog.setView(view);
        Dialog dialog1 = dialog.create();
        dialog1.show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myName = name.getText().toString();
                String myAge = age.getText().toString();
                String myPhone = phone.getText().toString();
                String myRole = role.getText().toString();
                employeeDetail employee = new employeeDetail(myName, myAge, myPhone, myRole);
                ref = FirebaseDatabase.getInstance().getReference("clinic").child("employee");
                ref.child(myName).setValue(employee);
                if(act == "add"){
                    Toast.makeText(MainAdminEmployee.this, "Successful added employee", Toast.LENGTH_LONG).show();
                    name.setText("");
                    age.setText("");
                    phone.setText("");
                    role.setText("");
                }else{
                    Toast.makeText(MainAdminEmployee.this, "Successful updated employee", Toast.LENGTH_LONG).show();
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }
    class customAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return arrayName.size();
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
            View v = getLayoutInflater().inflate(R.layout.employee_listview, null);
            TextView name = (TextView)v.findViewById(R.id.listEmployeeDetailName);
            TextView age = (TextView)v.findViewById(R.id.listEmployeeDetailAge);
            TextView phone = (TextView)v.findViewById(R.id.listEmployeeDetailPhone);
            TextView role = (TextView)v.findViewById(R.id.listEmployeeDetailRole);

            name.setText(arrayName.get(position));
            age.setText(arrayAge.get(position));
            phone.setText(arrayPhone.get(position));
            role.setText(arrayRole.get(position));
            return v;
        }
    }
}