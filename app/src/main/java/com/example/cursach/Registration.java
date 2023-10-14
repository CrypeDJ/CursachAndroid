package com.example.cursach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.cursach.models.User;
import com.example.cursach.models.Validation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registration extends AppCompatActivity {

    FirebaseAuth reg;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout regWindow;
    EditText name, bornDate, email, password, passAgain;
    Button btnReg;
    Spinner spSex;
    ProgressDialog progressBar ;
    String[] sex = {"мужской", "женский", "трансгендер", "не определился"};
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_registration);

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sex);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSex = (Spinner) findViewById(R.id.spSex);

        spSex.setAdapter(sexAdapter);

        sexAdapter.notifyDataSetChanged();
        progressBar = new ProgressDialog(this);

        name = findViewById(R.id.name);
        bornDate = findViewById(R.id.date);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.pass_reg);
        passAgain = findViewById(R.id.confirm_pass);
        spSex = findViewById(R.id.spSex);
        btnReg = findViewById(R.id.btn_reg);
        regWindow = findViewById(R.id.reg_window);

        reg = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = name.getText().toString();
                String bornDateString = bornDate.getText().toString();
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                String passAgainString = passAgain.getText().toString();
                String spSexString = spSex.getSelectedItem().toString();

                registration();
            }
        });
    }

    private void registration() {

        if(TextUtils.isEmpty(name.getText().toString())){
            Snackbar.make(regWindow, "Введите имя", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(bornDate.getText().toString())){
            Snackbar.make(regWindow, "Выберете дату рождения", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email.getText().toString())){
            Snackbar.make(regWindow, "Введите почту", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (Validation.isValidEmail(email.getText().toString())){
            Snackbar.make(regWindow, "Некорректно введена почта", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(password.getText().toString().length()<6){
            Snackbar.make(regWindow, "Пароль должен содержать не менее 6 символов", Snackbar.LENGTH_SHORT).show();
            password.setText("");
            passAgain.setText("");
            return;
        }
        if(Validation.isValidPassword(password.getText().toString(),passAgain.getText().toString())){
            Snackbar.make(regWindow, "Пароли не совпадают, попробуйте ещё раз ", Snackbar.LENGTH_SHORT).show();
            password.setText("");
            passAgain.setText("");
            return;
        }
        progressBar.show();
        reg.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();
                        user.setEmail(email.getText().toString());
                        user.setDate(bornDate.getText().toString());
                        user.setName(name.getText().toString());
                        user.setSex(spSex.getSelectedItem().toString());
                        user.setPassword(password.getText().toString());
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(regWindow,"Пользователь добавлен", Snackbar.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registration.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.cancel();
                                Snackbar.make(regWindow, "Ошибка авторизации. " + e.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
    }


}