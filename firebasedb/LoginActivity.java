package com.example.app.firebasedb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email , password;
    FirebaseAuth auth;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login2);
        email = findViewById(R.id.email);
        error = findViewById(R.id.error);
        password = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
    }

    public void login(View c) {
        String userEmail = email.getText().toString();
        String userPass = password.getText().toString();
        auth.signInWithEmailAndPassword(userEmail , userPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this , DashboardActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            String err = task.getException().toString();
                            error.setText(err);
                            Toast.makeText(LoginActivity.this, err , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}