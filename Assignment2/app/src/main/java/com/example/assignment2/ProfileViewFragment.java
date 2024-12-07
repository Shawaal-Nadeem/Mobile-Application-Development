package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileViewFragment extends Fragment {

    private TextView tvUsername, tvEmail, tvNotifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_view, container, false);

        // Initialize UI components
        tvUsername = view.findViewById(R.id.tv_username);
        tvEmail = view.findViewById(R.id.tv_email);
        tvNotifications = view.findViewById(R.id.tv_notifications);

        // Load and display preferences
        loadPreferences();

        return view;
    }

    private void loadPreferences() {
        // Retrieve SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Get stored values
        String username = sharedPreferences.getString("username", "No Username");
        String email = sharedPreferences.getString("email", "No Email");
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications", false);

        // Display retrieved values in TextViews
        tvUsername.setText("Username: " + username);
        tvEmail.setText("Email: " + email);
        tvNotifications.setText("Notifications: " + (notificationsEnabled ? "Enabled" : "Disabled"));
    }
}
