package com.assignment1.fitnesstrakingappproject;

public class Workout1 {
    private String name;
    private String description;
    private int duration;
    private int imageResId;

    public Workout1(String name, String description, int duration, int imageResId) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.imageResId = imageResId;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getDuration() { return duration; }
    public int getImageResId() { return imageResId; }
}
