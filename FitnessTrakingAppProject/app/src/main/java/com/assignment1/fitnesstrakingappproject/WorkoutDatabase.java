package com.assignment1.fitnesstrakingappproject;

import java.util.ArrayList;
import java.util.List;

public class WorkoutDatabase {

    public List<Workout1> getFromDb() {
        List<Workout1> workoutData = new ArrayList<>();

        // Example workouts with predefined data
        workoutData.add(new Workout1("Push Up", "A basic exercise for upper body strength", 30, R.drawable.pushups));
        workoutData.add(new Workout1("Squat", "Great for leg strength", 45, R.drawable.squats));
        workoutData.add(new Workout1("Plank", "Core stability exercise", 60, R.drawable.plank));
        workoutData.add(new Workout1("Burpee", "Full body exercise", 20, R.drawable.burpees));
        workoutData.add(new Workout1("Lunges", "Leg strength exercise", 30, R.drawable.lungs));
        workoutData.add(new Workout1("Jumping Jacks", "Full body warm-up exercise", 15, R.drawable.jumpingjacks));
        workoutData.add(new Workout1("Mountain Climbers", "Cardio workout for endurance", 25, R.drawable.mountainclimber));
        workoutData.add(new Workout1("Deadlift", "Strength training for the back and legs", 50, R.drawable.deadlift));
        workoutData.add(new Workout1("Bicep Curls", "Isolated arm strength exercise", 20, R.drawable.biceps));
        workoutData.add(new Workout1("Tricep Dips", "Upper body exercise for triceps", 15, R.drawable.triceps));

        return workoutData;
    }
}
