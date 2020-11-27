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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    TextInputLayout username, password;
    TextView Register;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        Register = findViewById(R.id.Register);

        auth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPage.this, RegisterationPage.class));
                finish();
            }
        });
    }

    public void loginUser(View view) {

        String user = username.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(user)){
            username.setError("Field cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(pass)){
            password.setError("Field Cannot be Empty");
            return;
        }

        if (pass.length()<=6 || pass.contains(" ")){
            password.setError("Invalid Password");
            return;
        }

        auth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginPage.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginPage.this, AllActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginPage.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}