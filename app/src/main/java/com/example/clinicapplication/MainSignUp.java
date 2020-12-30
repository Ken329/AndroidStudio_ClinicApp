package com.example.clinicapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainSignUp extends AppCompatActivity {
    EditText username, password, age, phone;
    TextView back, signUp;

    DatabaseReference ref;
    AwesomeValidation validate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);
        getSupportActionBar().hide();

        username = findViewById(R.id.etSignUpName);
        password = findViewById(R.id.etSignUpPassword);
        age = findViewById(R.id.etSignUpAge);
        phone = findViewById(R.id.etSignUpPhone);
        back = findViewById(R.id.tvSignUpBack);
        signUp = findViewById(R.id.tvSignUpEnter);

        validate = new AwesomeValidation(ValidationStyle.BASIC);
        validate.addValidation(this, R.id.etSignUpName, ".{6,}", R.string.InvalidName);
        validate.addValidation(this, R.id.etSignUpPassword, ".{6,}", R.string.InvalidName);
        validate.addValidation(this, R.id.etSignUpAge, RegexTemplate.NOT_EMPTY, R.string.InvalidAge);
        validate.addValidation(this, R.id.etSignUpName, RegexTemplate.NOT_EMPTY, R.string.InvalidAge);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack(v);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myUsername = username.getText().toString();
                String myPassword = password.getText().toString();
                String myAge = age.getText().toString();
                String myPhone = phone.getText().toString();
                if(validate.validate()){
                    userDetail user = new userDetail(myUsername, myPassword, myAge, myPhone);
                    ref = FirebaseDatabase.getInstance().getReference("user");
                    ref.child(myUsername).setValue(user);
                    Toast.makeText(MainSignUp.this, "Successful Sign Up", Toast.LENGTH_LONG).show();
                    goBack(v);
                }else{
                    Toast.makeText(MainSignUp.this, "Please check your field", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void goBack(View v){
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }
}