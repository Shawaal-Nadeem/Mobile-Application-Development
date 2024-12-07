package com.example.assignment2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserSettingsFragment extends Fragment {

    private EditText etUsername, etEmail, etPassword;
    private Switch switchNotifications;
    private Button btnSave, btnReset;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_settings, container, false);

        // Initialize UI components
        etUsername = view.findViewById(R.id.et_username);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        switchNotifications = view.findViewById(R.id.switch_notifications);
        btnSave = view.findViewById(R.id.btn_save);
        btnReset = view.findViewById(R.id.btn_reset);

        // Load previously saved preferences
        loadPreferences();

        // Set up Save button click listener
        btnSave.setOnClickListener(v -> savePreferences());

        // Set up Reset button click listener
        btnReset.setOnClickListener(v -> resetPreferences());

        return view;
    }

    private void savePreferences() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        boolean notificationsEnabled = switchNotifications.isChecked();

        // Validate input
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save to SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putBoolean("notifications", notificationsEnabled);
        editor.apply();

        Toast.makeText(getContext(), "Preferences saved", Toast.LENGTH_SHORT).show();
    }

    private void resetPreferences() {
        // Clear preferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Clear UI
        etUsername.setText("");
        etEmail.setText("");
        switchNotifications.setChecked(false);

        Toast.makeText(getContext(), "Preferences reset", Toast.LENGTH_SHORT).show();
    }

    private void loadPreferences() {
        // Load preferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        boolean notificationsEnabled = sharedPreferences.getBoolean("notifications", false);

        // Set values to UI
        etUsername.setText(username);
        etEmail.setText(email);
        switchNotifications.setChecked(notificationsEnabled);
    }
}
