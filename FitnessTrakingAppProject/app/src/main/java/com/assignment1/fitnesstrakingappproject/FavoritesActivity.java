package com.assignment1.fitnesstrakingappproject;

import android.os.Bundle;
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
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private FavouriteAdapter workoutAdapter;
    private List<Workout> workoutList;
    private DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorites);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workoutList = new ArrayList<>();
        workoutAdapter = new FavouriteAdapter(workoutList);
        recyclerView.setAdapter(workoutAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        // Retrieve the userId from the Intent
        String userId = firebaseAuth.getUid();

        // Initialize the Firebase reference
        assert userId != null;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("workOuts");

        // Fetch favorite workouts from Firebase
        fetchFavoriteWorkouts();
    }

    private void fetchFavoriteWorkouts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                workoutList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Workout workout = snapshot.getValue(Workout.class);
                    if (workout != null) {
                        workoutList.add(workout);
                    }
                }
                workoutAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FavoritesActivity.this, "Failed to load favorites: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
