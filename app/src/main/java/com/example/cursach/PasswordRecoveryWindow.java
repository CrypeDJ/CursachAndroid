package com.example.cursach;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cursach.models.Validation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class PasswordRecoveryWindow extends AppCompatActivity {

    Button btnSendMessage;
    FirebaseAuth reg;
    FirebaseDatabase db;
    DatabaseReference users;
    EditText emailToSend;
    RelativeLayout recoveryWindow;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery_window);

        emailToSend = findViewById(R.id.email_recovery);
        recoveryWindow = findViewById(R.id.recovery_window);
        btnSendMessage = findViewById(R.id.btn_send_recovery);

        progressDialog = new ProgressDialog(this);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(view);
            }
        });
    }


    public void sendMessage(View v){
        if (Validation.isValidEmail(emailToSend.getText().toString())){
            Snackbar.make(recoveryWindow, "Введите email", Snackbar.LENGTH_SHORT).show();
            return;
        }

        try {
            progressDialog.show();
                reg.sendPasswordResetEmail(emailToSend.getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Snackbar.make(recoveryWindow, "Код отправлен на почту", Snackbar.LENGTH_SHORT).show();
                                Intent intent = new Intent(PasswordRecoveryWindow.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Snackbar.make(recoveryWindow, "Ошибка." + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });



        } catch (Exception e) {
            progressDialog.cancel();
            Snackbar.make(recoveryWindow, e.getMessage(),Snackbar.LENGTH_SHORT).show();}
    }
}