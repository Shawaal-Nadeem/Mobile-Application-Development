package com.assignment1.fitnesstrakingappproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private List<workout> workoutList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(workout workout);
    }

    public WorkoutAdapter(List<workout> workoutList) {
        this.workoutList = workoutList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        workout currentWorkout = workoutList.get(position);
        holder.workoutNameText.setText(currentWorkout.getName());
        holder.workoutDescriptionText.setText(currentWorkout.getDescription());
        holder.workoutDurationText.setText(String.valueOf(currentWorkout.getDuration()) + " mins");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(currentWorkout);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView workoutNameText;
        TextView workoutDescriptionText;
        TextView workoutDurationText;

        WorkoutViewHolder(View itemView) {
            super(itemView);
            workoutNameText = itemView.findViewById(R.id.workoutNameText);
            workoutDescriptionText = itemView.findViewById(R.id.workoutDescriptionText);
            workoutDurationText = itemView.findViewById(R.id.workoutDurationText);
        }
    }
}
