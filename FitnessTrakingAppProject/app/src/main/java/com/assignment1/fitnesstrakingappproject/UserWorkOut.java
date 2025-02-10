package com.assignment1.fitnesstrakingappproject;

public class UserWorkOut {
    private String key;
    private String name;
    private String description;
    private String duration;

    public UserWorkOut() {

    }

    public UserWorkOut(String name, String description, String duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }
}
