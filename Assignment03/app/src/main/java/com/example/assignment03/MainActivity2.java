package com.example.assignment03;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeatureActivity extends AppCompatActivity {

    private static final String GPT_API_URL = "https://api.openai.com/";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String WEATHER_API_KEY = "YOUR_WEATHER_API_KEY";
    private static final String GPT_API_KEY = "YOUR_GPT_API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnGptApi).setOnClickListener(v -> callGptApi());
        findViewById(R.id.btnWeatherForecast).setOnClickListener(v -> callWeatherApi());
    }

    private void callGptApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GPT_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<String> call = apiService.getGptResponse("What is AI?", GPT_API_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FeatureActivity.this, "GPT Response: " + response.body(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FeatureActivity.this, "GPT API Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(FeatureActivity.this, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callWeatherApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEATHER_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<String> call = apiService.getWeather("London", WEATHER_API_KEY);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FeatureActivity.this, "Weather Data: " + response.body(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FeatureActivity.this, "Weather API Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(FeatureActivity.this, "API Call Failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}