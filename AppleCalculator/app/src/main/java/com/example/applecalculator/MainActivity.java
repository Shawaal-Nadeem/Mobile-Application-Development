package com.example.applecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    double firstNum = 0;
    double secondNum = 0;
    String operation = "";
    boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button num0 = findViewById(R.id.num0);
        Button num1 = findViewById(R.id.num1);
        Button num2 = findViewById(R.id.num2);
        Button num3 = findViewById(R.id.num3);
        Button num4 = findViewById(R.id.num4);
        Button num5 = findViewById(R.id.num5);
        Button num6 = findViewById(R.id.num6);
        Button num7 = findViewById(R.id.num7);
        Button num8 = findViewById(R.id.num8);
        Button num9 = findViewById(R.id.num9);
        Button point = findViewById(R.id.point);
        Button ac = findViewById(R.id.ac);
        Button divide = findViewById(R.id.div);
        Button multiply = findViewById(R.id.mul);
        Button minus = findViewById(R.id.min);
        Button plus = findViewById(R.id.plus);
        Button equal = findViewById(R.id.equal);

        TextView screen = findViewById(R.id.screen);

        ArrayList<Button> nums = new ArrayList<>();
        nums.add(num0);
        nums.add(num1);
        nums.add(num2);
        nums.add(num3);
        nums.add(num4);
        nums.add(num5);
        nums.add(num6);
        nums.add(num7);
        nums.add(num8);
        nums.add(num9);

        for (Button b : nums) {
            b.setOnClickListener(view -> {
                // If an operator was pressed, start appending the second number
                if (isOperatorPressed) {
                    screen.setText(screen.getText().toString() + b.getText().toString());
                } else {
                    // Append the number to the screen
                    if (!screen.getText().toString().equals("0")) {
                        screen.setText(screen.getText().toString() + b.getText().toString());
                    } else {
                        screen.setText(b.getText().toString());
                    }
                }
            });
        }

        ArrayList<Button> operators = new ArrayList<>();
        operators.add(plus);
        operators.add(minus);
        operators.add(multiply);
        operators.add(divide);

        for (Button b : operators) {
            b.setOnClickListener(view -> {
                if (!isOperatorPressed) {
                    // Capture the first number and set operator
                    firstNum = Double.parseDouble(screen.getText().toString());
                    operation = b.getText().toString();
                    screen.setText(screen.getText().toString() + " " + operation + " ");
                    isOperatorPressed = true; // Start accepting second number
                }
            });
        }

        point.setOnClickListener(view -> {
            if (!screen.getText().toString().contains(".")) {
                screen.setText(screen.getText().toString() + ".");
            }
        });

        equal.setOnClickListener(view -> {
            try {
                // Split screen text by spaces to get second number
                String[] parts = screen.getText().toString().split(" ");
                if (parts.length < 3) return; // Avoid errors if parts are incomplete
                secondNum = Double.parseDouble(parts[2]);
                double result = 0;

                switch (operation) {
                    case "+":
                        result = firstNum + secondNum;
                        break;
                    case "-":
                        result = firstNum - secondNum;
                        break;
                    case "X":
                        result = firstNum * secondNum;
                        break;
                    case "÷":
                        if (secondNum != 0) {
                            result = firstNum / secondNum;
                        } else {
                            screen.setText("Error");
                            return;
                        }
                        break;
                }

                screen.setText(String.valueOf(result));
                firstNum = result; // Update firstNum with result for further calculations
                isOperatorPressed = false;
            } catch (Exception e) {
                screen.setText("Error");
            }
        });

        ac.setOnClickListener(view -> {
            firstNum = 0;
            secondNum = 0;
            operation = "";
            isOperatorPressed = false;
            screen.setText("0");
        });
    }
}
