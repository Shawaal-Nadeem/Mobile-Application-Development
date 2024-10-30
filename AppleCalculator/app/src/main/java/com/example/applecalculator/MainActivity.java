package com.example.applecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

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
        Button percentage = findViewById(R.id.mod);
        Button equal = findViewById(R.id.equal);
        Button plusOrMinus = findViewById(R.id.plusOrMinus);

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

        // Handle numeric button clicks
        for (Button b : nums) {
            b.setOnClickListener(view -> {
                if (!screen.getText().toString().equals("0")) {
                    screen.setText(screen.getText().toString() + b.getText().toString());
                } else {
                    screen.setText(b.getText().toString());
                }
            });
        }

        // Handle operators
        ArrayList<Button> operators = new ArrayList<>();
        operators.add(plus);
        operators.add(minus);
        operators.add(multiply);
        operators.add(divide);
        operators.add(percentage);

        for (Button b : operators) {
            b.setOnClickListener(view -> {
                String currentText = screen.getText().toString();
                if (!currentText.isEmpty() && !currentText.endsWith(" ")) {
                    screen.setText(currentText + " " + b.getText().toString() + " ");
                }
            });
        }

        point.setOnClickListener(view -> {
            String currentText = screen.getText().toString();

            // Split the current text by spaces to isolate the last operand
            String[] tokens = currentText.split(" ");
            String lastToken = tokens[tokens.length - 1];

            // Only add a decimal point if the last operand doesn't already contain one
            if (!lastToken.contains(".")) {
                screen.setText(currentText + ".");
            }
        });


        equal.setOnClickListener(view -> {
            try {
                String expression = screen.getText().toString();
                double result = calculateExpression(expression);

                // Display result without decimal if it's a whole number
                if (result == Math.floor(result)) {
                    screen.setText(String.valueOf((int) result));
                } else {
                    screen.setText(String.valueOf(result));
                }
            } catch (Exception e) {
                screen.setText("Error");
            }
        });

        ac.setOnClickListener(view -> screen.setText("0"));

        // Toggle the sign of the current number
        plusOrMinus.setOnClickListener(view -> {
            String currentText = screen.getText().toString();
            if (!currentText.isEmpty() && !currentText.equals("0")) {
                try {
                    double currentNum = Double.parseDouble(currentText);
                    currentNum = -currentNum; // Toggle the sign
                    screen.setText(String.valueOf(currentNum));
                } catch (NumberFormatException e) {
                    screen.setText("Error");
                }
            }
        });
    }

    private double calculateExpression(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operations = new Stack<>();
        String[] tokens = expression.split(" ");

        for (String token : tokens) {
            if (isNumeric(token)) {
                numbers.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                while (!operations.isEmpty() && precedence(operations.peek()) >= precedence(token.charAt(0))) {
                    double b = numbers.pop();
                    double a = numbers.pop();
                    char op = operations.pop();
                    numbers.push(applyOperation(a, b, op));
                }
                operations.push(token.charAt(0));
            }
        }

        while (!operations.isEmpty()) {
            double b = numbers.pop();
            double a = numbers.pop();
            char op = operations.pop();
            numbers.push(applyOperation(a, b, op));
        }
        return numbers.pop();
    }

    private boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == 'X' || c == '÷' || c == '%';
    }

    private int precedence(char op) {
        if (op == 'X' || op == '÷' || op == '%') return 2;
        if (op == '+' || op == '-') return 1;
        return 0;
    }

    private double applyOperation(double a, double b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case 'X':
                return a * b;
            case '÷':
                if (b == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            case '%':
                return a % b;
        }
        return 0;
    }
}
