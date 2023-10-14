package com.example.cursach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import com.example.cursach.models.Validation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewPassword extends AppCompatActivity {
    FirebaseAuth reg;
    FirebaseDatabase db;
    DatabaseReference users;
    FirebaseUser user;
    EditText newPassword, confirmNewPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);

        newPassword = findViewById(R.id.new_pass_create);
        confirmNewPassword = findViewById(R.id.confirm_new_pass);

    }


}