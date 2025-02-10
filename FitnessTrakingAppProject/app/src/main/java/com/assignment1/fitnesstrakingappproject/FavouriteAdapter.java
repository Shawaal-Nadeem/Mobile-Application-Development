package com.assignment1.fitnesstrakingappproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder> {

    private final List<Workout> workoutList;

    public FavouriteAdapter(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_workout, parent, false);
        return new FavouriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.workoutName.setText(workout.getName());
        holder.workoutDescription.setText(workout.getDescription());
        holder.workoutDuration.setText(workout.getDuration());
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder {
        TextView workoutName;
        TextView workoutDescription;
        TextView workoutDuration;

        public FavouriteViewHolder(View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workoutName);
            workoutDescription = itemView.findViewById(R.id.workoutDescription);
            workoutDuration = itemView.findViewById(R.id.workoutDuration);
        }
    }
}
