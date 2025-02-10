package com.assignment1.fitnesstrakingappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    TextView appName,login,signupText,signinText;
    EditText username,password;
    Button loginbtn;

    LoginDatabase loginDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        logo=findViewById(R.id.logo);
        appName=findViewById(R.id.appName);
        login=findViewById(R.id.login);
        signupText=findViewById(R.id.signupText);
        signinText=findViewById(R.id.signinText);
        username=findViewById(R.id.username);
        loginbtn=findViewById(R.id.loginbtn);
        password=findViewById(R.id.password);
      //  ListView workoutListView = findViewById(R.id.workoutListView);


        loginDatabase=new LoginDatabase(this,null,null);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //storing username&Password
                String name=username.getText().toString();
                String pas = password.getText().toString();
                if(name.isEmpty()||pas.isEmpty()) {
                    Toast.makeText(MainActivity.this,"Please Enter both fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    boolean isVerified = loginDatabase.checkUserCredentials(name, pas);
                    if (isVerified) {
                        Toast.makeText(MainActivity.this, "SignIn successfull", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, WorkoutActivity.class);
//                        startActivity(intent);
                        Intent intent = new Intent(MainActivity.this, WorkoutDetailActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }

        });

        //SignUp Functionality
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Sign Up Clicked", Toast.LENGTH_SHORT).show();
                //To go to next page
                Intent intent=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });


    }
}