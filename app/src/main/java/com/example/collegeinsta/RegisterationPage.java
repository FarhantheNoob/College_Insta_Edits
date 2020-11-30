package com.example.collegeinsta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterationPage extends AppCompatActivity {

    TextInputLayout fullName, username, emailID, password, phoneNo;
    TextView loginTab;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration_page);

        fullName = findViewById(R.id.FullName);
        username = findViewById(R.id.Username);
        emailID = findViewById(R.id.EmailAddress);
        phoneNo = findViewById(R.id.PhoneNo);
        password = findViewById(R.id.Password);
        loginTab = findViewById(R.id.Login);

        auth = FirebaseAuth.getInstance();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            Toast.makeText(this, "Registeration Successfull", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterationPage.this, NewsFeed.class));
            finish();
        }else{
            Toast.makeText(this, "Registeration Failed", Toast.LENGTH_SHORT).show();
        }

        loginTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterationPage.this, LoginPage.class));
                finish();
            }
        });
    }

    public void registerUser(View view) {

        String name = fullName.getEditText().getText().toString().trim();
        String user = username.getEditText().getText().toString().trim();
        String phone = phoneNo.getEditText().getText().toString().trim();
        String email = emailID.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            emailID.setError("Name Required");
            return;
        }

        if (TextUtils.isEmpty(user)){
            emailID.setError("Username Required");
            return;
        }

        if (TextUtils.isEmpty(phone)){
            emailID.setError("Phone Number Required");
            return;
        }

        if (TextUtils.isEmpty(email)){
            emailID.setError("Email Address Required");
            return;
        }

        if (TextUtils.isEmpty(pass)){
            password.setError("Password Required");
            return;
        }

        if (pass.length()<=6 || pass.contains(" ")){
            password.setError("Invalid Password");
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(RegisterationPage.this, NewsFeed.class));
                    finish();
                }else{
                    Toast.makeText(RegisterationPage.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}