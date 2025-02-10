package com.assignment1.fitnesstrakingappproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutDetailActivity extends AppCompatActivity {
    private RecyclerView workoutRecyclerView;
    private WorkoutAdapter1 workoutAdapter;
    private List<Workout1> workoutList;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        workoutRecyclerView = findViewById(R.id.workoutRecyclerView);
        nextButton = findViewById(R.id.nextButton);
        workoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                workoutRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        workoutRecyclerView.addItemDecoration(dividerItemDecoration);

        // Use WorkoutDatabase to get workout data
        WorkoutDatabase workoutDatabase = new WorkoutDatabase();
        workoutList = workoutDatabase.getFromDb();

        workoutAdapter = new WorkoutAdapter1(this, workoutList);
        workoutRecyclerView.setAdapter(workoutAdapter);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkoutDetailActivity.this, WorkoutActivity.class);
                startActivity(intent);
            }
        });
    }
}
