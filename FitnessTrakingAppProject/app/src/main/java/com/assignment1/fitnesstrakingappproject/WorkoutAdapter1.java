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
import java.util.List;

public class WorkoutAdapter1 extends RecyclerView.Adapter<WorkoutAdapter1.WorkoutViewHolder> {
    private Context context;
    private List<Workout1> workoutList;
    private LoginDatabase database; // Declare the database instance

    public WorkoutAdapter1(Context context, List<Workout1> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
        database = new LoginDatabase(context, null, null);
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout1 workout = workoutList.get(position);
        holder.workoutNameDetail.setText(workout.getName());
        holder.workoutDescriptionDetail.setText(workout.getDescription());
        holder.workoutDurationDetail.setText("Duration: " + workout.getDuration() + " mins");
        holder.workoutImage.setImageResource(workout.getImageResId());


        holder.addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkoutToFavorites(workout);
            }
        });
    }
    private void addWorkoutToFavorites(Workout1 workout) {
        // Call the database method to add to favorites
        boolean success = database.addWorkoutToFavorites(workout.getName(), workout.getDescription(), workout.getDuration());


            Toast.makeText(context, "Added to Favorites!", Toast.LENGTH_SHORT).show();

    }


    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {
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
}
