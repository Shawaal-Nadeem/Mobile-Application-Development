package com.assignment1.fitnesstrakingappproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    EditText username,password;
    Button signUpButton;
    LoginDatabase loginDatabase;
    Context context;
TextView signintext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        signUpButton=findViewById(R.id.Signupbtn);
signintext=findViewById(R.id.signinText);
        loginDatabase=new LoginDatabase(this,null,null);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //storing username&Password
                String name = username.getText().toString();
                String pas = password.getText().toString();
                if (name.isEmpty() || pas.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please Enter both fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (loginDatabase.userExists(name)) {
                        Toast.makeText(SignupActivity.this, "Username Already exist", Toast.LENGTH_SHORT).show();
                    } else {
                        loginDatabase.insert(name, pas);
                        Toast.makeText(SignupActivity.this, "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, WorkoutDetailActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        signintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Sign Up Clicked", Toast.LENGTH_SHORT).show();
                //To go to next page
                Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}