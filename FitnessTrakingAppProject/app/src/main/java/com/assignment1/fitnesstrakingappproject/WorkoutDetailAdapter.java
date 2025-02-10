package com.assignment1.fitnesstrakingappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class WorkoutDetailAdapter extends RecyclerView.Adapter<WorkoutDetailAdapter.WorkoutViewHolder> {
    private final Context context;
    private final List<Workout> workoutList;
    private final String userId;

    public WorkoutDetailAdapter(Context context, List<Workout> workoutList, String userId) {
        this.context = context;
        this.workoutList = workoutList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.workoutNameDetail.setText(workout.getName());
        holder.workoutDescriptionDetail.setText(workout.getDescription());
        String message = "Duration: " + workout.getDuration() + " minutes";
        holder.workoutDurationDetail.setText(message);
        holder.workoutImage.setImageResource(workout.getImageResId());
        holder.addToFavoriteButton.setOnClickListener(v -> addWorkoutToFavorites(workout));
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView workoutNameDetail;
        TextView workoutDescriptionDetail;
        TextView workoutDurationDetail;
        ImageView workoutImage;
        Button addToFavoriteButton;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutNameDetail = itemView.findViewById(R.id.workoutNameDetail);
            workoutDescriptionDetail = itemView.findViewById(R.id.workoutDescriptionDetail);
            workoutDurationDetail = itemView.findViewById(R.id.workoutDurationDetail);
            workoutImage = itemView.findViewById(R.id.workoutImage);
            addToFavoriteButton = itemView.findViewById(R.id.addToFavoriteButton);
        }
    }

    private void addWorkoutToFavorites(Workout workout) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        ref.child("workOuts").setValue(workout)
                .addOnCompleteListener(dbTask -> {
                    if (dbTask.isSuccessful()) {
                        Toast.makeText(context, "Workout added successfully.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to add workout: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
