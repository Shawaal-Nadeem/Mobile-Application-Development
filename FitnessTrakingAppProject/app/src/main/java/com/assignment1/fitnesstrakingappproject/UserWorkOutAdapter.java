package com.assignment1.fitnesstrakingappproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserWorkOutAdapter extends RecyclerView.Adapter<UserWorkOutAdapter.WorkoutViewHolder> {
    private final Context context;
    private List<UserWorkOut> workoutList;
    private final DatabaseReference databaseReference;

    public UserWorkOutAdapter(Context context, List<UserWorkOut> workoutList, String userId) {
        this.context = context;
        this.workoutList = workoutList;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("MyWorkOuts");
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        if (workoutList == null || workoutList.isEmpty()) {
            return;
        }

        UserWorkOut workout = workoutList.get(position);
        if (workout != null) {
            holder.workoutNameText.setText(workout.getName());
            holder.workoutDescriptionText.setText(workout.getDescription());
            String message = "Duration: " + workout.getDuration() + " minutes";
            holder.workoutDurationText.setText(message);
            holder.deleteButton.setOnClickListener(v -> deleteWorkout(workout, position));

        }
    }

    private void deleteWorkout(UserWorkOut workout, int position) {
        databaseReference.child(workout.getKey()).removeValue()
                .addOnSuccessListener(aVoid -> {
                    workoutList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Workout deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(context, "Failed to delete workout", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView workoutNameText, workoutDescriptionText, workoutDurationText;
        ImageButton deleteButton;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutNameText = itemView.findViewById(R.id.workoutNameText);
            workoutDescriptionText = itemView.findViewById(R.id.workoutDescriptionText);
            workoutDurationText = itemView.findViewById(R.id.workoutDurationText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public void updateList(List<UserWorkOut> newWorkoutList){
        this.workoutList = newWorkoutList;
        notifyDataSetChanged();
    }
}
