package com.assignment1.fitnesstrakingappproject;
public class workout {
    private int id;
    private String name;
    private String description;
    private String duration;

    public workout(int id, String name, String description, String duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getDuration() { return duration; }
}
