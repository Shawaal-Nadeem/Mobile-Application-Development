package com.assignment1.fitnesstrakingappproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import User.User;

public class LoginDatabase extends SQLiteOpenHelper {
    String username;
    String password;
    Context context;

    public LoginDatabase(Context context, String username, String password){
        super(context, "LoginDatabase1", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LoginTable (ID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT, Password TEXT)");
        db.execSQL("CREATE TABLE AddToFavorite (ID INTEGER PRIMARY KEY AUTOINCREMENT, WorkoutID INTEGER, Name TEXT, Description TEXT, Duration TEXT, isFavorite INTEGER DEFAULT 1)");
        db.execSQL("CREATE TABLE WorkoutTable (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Description TEXT, Duration TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "LoginTable");
        db.execSQL("DROP TABLE IF EXISTS " + "WorkoutTable");
    }

    long insert(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("Password", password);
        long res = db.insert("LoginTable", null, cv);
        return res;
    }

    public List<User> read() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> u = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM LoginTable", null);

        if (c.moveToFirst()) {
            do {
                String username = c.getString(1);
                String password = c.getString(2);
                User lgnTable = new User(username, password);
                u.add(lgnTable);
            } while (c.moveToNext());
        }

        c.close();
        return u;
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("LoginTable", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                String dbUsername = cursor.getString(1);
                String dbPassword = cursor.getString(2);
                if (dbUsername.equals(username) && dbPassword.equals(password)) {
                    cursor.close();
                    return true;
                }
                cursor.moveToNext();
            }
            cursor.close();
        }

        return false;
    }

    public boolean userExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("LoginTable", new String[]{"Username"}, "Username = ?", new String[]{name}, null, null, null);
        boolean userExists = c.getCount() > 0;
        return userExists;
    }

    public void insertWorkout(String name, String description, String duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Description", description);
        values.put("Duration", duration);
        long result = db.insert("WorkoutTable", null, values);
        db.close();

        if (result == -1) {
            Log.d("Database", "Error adding workout");
        } else {
            Log.d("Database", "Workout added successfully");
        }
    }

    public boolean updateWorkout(int id, String newName, String newDescription, String newDuration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", newName);
        values.put("Description", newDescription);
        values.put("Duration", newDuration);
        return db.update("WorkoutTable", values, "ID = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deleteWorkout(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("WorkoutTable", "ID = ?", new String[]{String.valueOf(id)}) > 0;
    }

    public List<workout> searchWorkouts(String name, String minDuration, String maxDuration) {
        List<workout> filteredWorkouts = new ArrayList<>();
        String query = "SELECT * FROM WorkoutTable WHERE 1=1";

        if (!name.isEmpty()) {
            query += " AND Name LIKE '%" + name + "%'";
        }
        if (!minDuration.isEmpty()) {
            query += " AND Duration >= " + minDuration;
        }
        if (!maxDuration.isEmpty()) {
            query += " AND Duration <= " + maxDuration;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String workoutName = cursor.getString(1);
                String description = cursor.getString(2);
                String duration = cursor.getString(3);
                filteredWorkouts.add(new workout(id, workoutName, description, duration));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return filteredWorkouts;
    }

    public List<workout> readWorkouts() {
        List<workout> workoutList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("WorkoutTable", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String duration = cursor.getString(3);
                workout workout = new workout(id, name, description, duration);
                workoutList.add(workout);
                Log.d("WorkoutDB", "Added workout: " + name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return workoutList;
    }

    public List<workout> readFilteredWorkouts(String nameFilter, int minDuration, int maxDuration) {
        List<workout> filteredWorkouts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder selection = new StringBuilder();

        if (!nameFilter.isEmpty()) {
            selection.append("Name LIKE '%").append(nameFilter).append("%' ");
        }

        if (minDuration > 0) {
            if (selection.length() > 0) selection.append("AND ");
            selection.append("Duration >= ").append(minDuration).append(" ");
        }

        if (maxDuration < Integer.MAX_VALUE) {
            if (selection.length() > 0) selection.append("AND ");
            selection.append("Duration <= ").append(maxDuration).append(" ");
        }

        String selectionString = selection.toString();
        Cursor cursor = db.query("WorkoutTable", null, selectionString, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String duration = cursor.getString(3);
                filteredWorkouts.add(new workout(id, name, description, duration));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return filteredWorkouts;
    }

    public boolean addWorkoutToFavorites(String name, String description, int duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("duration", duration);
        values.put("isFavorite", 1);
        long result = db.insert("favorites", null, values);
        db.close();

        if (result == -1) {
            Log.e("Database Error", "Failed to insert data into favorites");
        }

        return result != -1;
    }

    public boolean isWorkoutFavorite(int workoutId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT isFavorite FROM WorkoutTable WHERE ID = ?", new String[]{String.valueOf(workoutId)});

        if (cursor != null && cursor.moveToFirst()) {
            int isFavorite = cursor.getInt(0);
            cursor.close();
            return isFavorite == 1;
        }
        return false;
    }

    public List<workout> getAllWorkouts() {
        List<workout> workoutList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query("WorkoutTable", null, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String description = cursor.getString(2);
                    String duration = cursor.getString(3);
                    workout w = new workout(id, name, description, duration);
                    workoutList.add(w);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return workoutList;
    }
}
