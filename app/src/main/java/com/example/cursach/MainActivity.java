package com.example.cursach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnAuth;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout authWindow;
    private EditText email, password;
    private Button bntAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main);

        btnAuth = findViewById(R.id.btn_enter);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        authWindow = findViewById(R.id.auth_window);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString();
                String passString = password.getText().toString();
                authorization();
            }
        });
    }

    private void authorization() {
        if(TextUtils.isEmpty(email.getText().toString())){
            Snackbar.make(authWindow, "Введите почту", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(password.getText().toString().length()<6){
            Snackbar.make(authWindow, "Пароль должен содержать не менее 6 символов", Snackbar.LENGTH_SHORT).show();
            return;
        }
        auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(MainActivity.this, MainScreen.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(authWindow, "Ошибка авторизации. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    public void createAccountBtn (View v){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

    public void recoveryPassword(View v){
        Intent intent = new Intent(this, PasswordRecoveryWindow.class);
        startActivity(intent);
    }
}
