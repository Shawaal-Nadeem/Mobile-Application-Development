package com.assignment1.fitnesstrakingappproject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UserWorkOutActivity extends AppCompatActivity {
    EditText workoutName, workoutDescription, workoutDuration;
    FirebaseAuth mAuth;
    Button addWorkoutButton;
    RecyclerView workoutRecyclerView;
    EditText searchName;
    Button resetBtn;
    List<UserWorkOut> UserWorkOuts;
    UserWorkOutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_workout);

        mAuth = FirebaseAuth.getInstance();

        workoutName = findViewById(R.id.workoutName);
        workoutDescription = findViewById(R.id.workoutDescription);
        workoutDuration = findViewById(R.id.workoutDuration);
        addWorkoutButton = findViewById(R.id.addWorkoutButton);
        workoutRecyclerView = findViewById(R.id.workoutRecyclerView);
        searchName = findViewById(R.id.searchName);
        resetBtn = findViewById(R.id.resetBtn);

        UserWorkOuts = new ArrayList<>();
        adapter = new UserWorkOutAdapter(this, UserWorkOuts, mAuth.getUid());
        workoutRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        workoutRecyclerView.setAdapter(adapter);

        loadWorkoutList();

        addWorkoutButton.setOnClickListener(view -> {
            String name = workoutName.getText().toString().trim();
            String description = workoutDescription.getText().toString().trim();
            String durationString = workoutDuration.getText().toString().trim();

            if (!name.isEmpty() && !description.isEmpty() && !durationString.isEmpty()) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                        .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child("MyWorkOuts");
                HashMap<String, Object> workOutInfo = new HashMap<>();
                workOutInfo.put("name", name);
                workOutInfo.put("description", description);
                workOutInfo.put("duration", durationString);

                String key = ref.push().getKey();
                workOutInfo.put("key", key);

                assert key != null;
                ref.child(key).setValue(workOutInfo)
                        .addOnCompleteListener(dbTask -> {
                            if (dbTask.isSuccessful()) {
                                Toast.makeText(UserWorkOutActivity.this, "WorkOut successfully added.", Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                Toast.makeText(UserWorkOutActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
            loadWorkoutList();
            clearFields();
        });

        searchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String name = searchName.getText().toString().trim();

                if (!name.isEmpty()) {
                    filterWorkouts(name);
                } else {
                    adapter.updateList(UserWorkOuts);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        resetBtn.setOnClickListener(v -> resetSearch());
    }

    private void filterWorkouts(String name) {
        List<UserWorkOut> filteredWorkouts = new ArrayList<>();
        for (UserWorkOut workout : UserWorkOuts) {
            if (workout.getName().toLowerCase().contains(name.toLowerCase())) {
                filteredWorkouts.add(workout);
            }
        }
        adapter.updateList(filteredWorkouts);
    }

    private void loadWorkoutList() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).child("MyWorkOuts");

        UserWorkOuts.clear();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserWorkOut workout = dataSnapshot.getValue(UserWorkOut.class);
                    if (workout != null) { // Ensure it's not null before adding
                        UserWorkOuts.add(workout);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter after data is updated
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log the error to debug issues with data retrieval
                Log.e("UserWorkOutActivity", "Error loading workout list", error.toException());
                Toast.makeText(UserWorkOutActivity.this, "Failed to load workouts.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        workoutName.setText("");
        workoutDescription.setText("");
        workoutDuration.setText("");
    }

    private void resetSearch() {
        adapter.updateList(UserWorkOuts);
    }
}
