package com.assignment1.fitnesstrakingappproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    EditText workoutName, workoutDescription, workoutDuration;
    Button addWorkoutButton, updateWorkoutButton, deleteWorkoutButton;
    RecyclerView workoutRecyclerView;
    LoginDatabase loginDatabase;
    EditText searchName, minDuration, maxDuration;
    Button searchBtn;

    List<workout> workouts;
    workout selectedWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        workoutName = findViewById(R.id.workoutName);
        workoutDescription = findViewById(R.id.workoutDescription);
        workoutDuration = findViewById(R.id.workoutDuration);
        addWorkoutButton = findViewById(R.id.addWorkoutButton);
        workoutRecyclerView = findViewById(R.id.workoutRecyclerView);
        updateWorkoutButton = findViewById(R.id.updateWorkoutButton);
        deleteWorkoutButton = findViewById(R.id.deleteWorkoutButton);
        searchName = findViewById(R.id.searchName);
        minDuration = findViewById(R.id.minDuration);
        maxDuration = findViewById(R.id.maxDuration);
        searchBtn = findViewById(R.id.searchBtn);

        loginDatabase = new LoginDatabase(this, null, null);
        workouts = new ArrayList<>();

        workoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadWorkoutList();

        addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = workoutName.getText().toString().trim();
                String description = workoutDescription.getText().toString().trim();
                String durationString = workoutDuration.getText().toString().trim();

                if (name.isEmpty() || description.isEmpty() || durationString.isEmpty()) {
                    Toast.makeText(WorkoutActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginDatabase.insertWorkout(name, description, durationString);
                loadWorkoutList();
                clearFields();
            }
        });

        updateWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedWorkout != null) {
                    String name = workoutName.getText().toString().trim();
                    String description = workoutDescription.getText().toString().trim();
                    String durationString = workoutDuration.getText().toString().trim();

                    if (name.isEmpty() || description.isEmpty() || durationString.isEmpty()) {
                        Toast.makeText(WorkoutActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    loginDatabase.updateWorkout(selectedWorkout.getId(), name, description, durationString);
                    loadWorkoutList();
                    clearFields();
                    selectedWorkout = null;
                }
            }
        });

        deleteWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedWorkout != null) {
                    loginDatabase.deleteWorkout(selectedWorkout.getId());
                    loadWorkoutList();
                    clearFields();
                    selectedWorkout = null;
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = searchName.getText().toString().trim();
                String minDurationString = minDuration.getText().toString().trim();
                String maxDurationString = maxDuration.getText().toString().trim();

                List<workout> searchResults = loginDatabase.searchWorkouts(name, minDurationString, maxDurationString);
                showSearchResultsDialog(searchResults);
            }
        });
    }

    private void loadWorkoutList() {
        workouts = loginDatabase.getAllWorkouts();
        WorkoutAdapter adapter = new WorkoutAdapter(workouts);

        adapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(workout workout) {
                selectedWorkout = workout;
                workoutName.setText(selectedWorkout.getName());
                workoutDescription.setText(selectedWorkout.getDescription());
                workoutDuration.setText(String.valueOf(selectedWorkout.getDuration()));
            }
        });

        workoutRecyclerView.setAdapter(adapter);
    }

    private void loadWorkoutList(String name, String minDuration, String maxDuration) {
        workouts = loginDatabase.searchWorkouts(name, minDuration, maxDuration);
        WorkoutAdapter adapter = new WorkoutAdapter(workouts);

        adapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(workout workout) {
                selectedWorkout = workout;
                workoutName.setText(selectedWorkout.getName());
                workoutDescription.setText(selectedWorkout.getDescription());
                workoutDuration.setText(String.valueOf(selectedWorkout.getDuration()));
            }
        });

        workoutRecyclerView.setAdapter(adapter);
    }

    private void clearFields() {
        workoutName.setText("");
        workoutDescription.setText("");
        workoutDuration.setText("");
    }

    private void showSearchResultsDialog(List<workout> workouts) {
        StringBuilder results = new StringBuilder();

        if (workouts.isEmpty()) {
            results.append("No workouts found.");
        } else {
            for (workout w : workouts) {
                results.append("Name: ").append(w.getName()).append("\n")
                        .append("Description: ").append(w.getDescription()).append("\n")
                        .append("Duration: ").append(w.getDuration()).append(" mins\n\n");
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("Search Results")
                .setMessage(results.toString())
                .setPositiveButton("OK", null)
                .show();
    }
}
