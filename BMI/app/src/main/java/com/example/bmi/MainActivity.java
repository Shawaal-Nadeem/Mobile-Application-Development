package com.example.bmi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText weightInput;
    private EditText heightInput;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        resultText = findViewById(R.id.resultText);
        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        String weightStr = weightInput.getText().toString();
        String heightStr = heightInput.getText().toString();

        if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
            double weight = Double.parseDouble(weightStr);
            double height = Double.parseDouble(heightStr);

            if (height > 0) {
                double bmi = weight / (height * height);
                displayBMI(bmi);
            } else {
                resultText.setText("Height is not valid");
            }
        } else {
            resultText.setText("Enter your weight and height.");
        }
    }

    private void displayBMI(double bmi) {
        String bmiVal;
        if (bmi<18.5){
            bmiVal ="Underweight";
        } else if (bmi<24.9){
            bmiVal ="Normal weight";
        } else if (bmi<29.9){
            bmiVal ="Overweight";
        } else if (bmi<34.9){
            bmiVal ="Mild Obesity";
        } else if (bmi>40){
            bmiVal ="Severe Obesity";
        }
        else {
            bmiVal ="Not Found";
        }

        String result = "BMI is: " +String.format("%.2f", bmi)+ "\n"+ bmiVal;
        resultText.setText(result);
    }
}