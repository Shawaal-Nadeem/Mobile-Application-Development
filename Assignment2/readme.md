# Multi-Fragment App with SharedPreferences

## Overview
This app allows users to personalize their experience by storing their preferences across multiple fragments. It uses **SharedPreferences** to persist user data (such as username, email, and notification settings) across sessions. The app features two fragments:
1. **UserSettingsFragment**: Allows users to input and save their preferences.
2. **ProfileViewFragment**: Displays the saved preferences when the user navigates to it.

The app includes a **BottomNavigationView** for seamless navigation between the fragments.

## Implementation

### 1. **SharedPreferences and Fragment Implementation**

#### **SharedPreferences**
- **Saving Preferences:**
  - In the `UserSettingsFragment`, when the user enters their preferences (username, email, and notifications), the data is saved using `SharedPreferences`. The `apply()` method is used to persist the data.
  
    ```java
    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("username", username);
    editor.putString("email", email);
    editor.putBoolean("notifications", notificationsEnabled);
    editor.apply();
    ```

- **Retrieving Preferences:**
  - In the `ProfileViewFragment`, saved preferences are retrieved using `SharedPreferences.getString()` and `SharedPreferences.getBoolean()` methods and displayed in the UI.
  
    ```java
    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
    String username = sharedPreferences.getString("username", "No Username");
    String email = sharedPreferences.getString("email", "No Email");
    boolean notificationsEnabled = sharedPreferences.getBoolean("notifications", false);
    ```

#### **Fragments**
- **UserSettingsFragment**:
  - This fragment allows the user to input preferences and save them to `SharedPreferences` using a "Save Preferences" button.
- **ProfileViewFragment**:
  - This fragment retrieves and displays the stored preferences when the user navigates to it.

#### **Fragment Navigation**
- A **BottomNavigationView** is used to switch between `UserSettingsFragment` and `ProfileViewFragment`. Fragment transactions are handled in the `MainActivity` using the `FragmentManager`.

    ```java
    Fragment selectedFragment = new UserSettingsFragment(); // or ProfileViewFragment
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragment_container, selectedFragment)
        .commit();
    ```

### 2. **Key Challenges and How I Overcame Them**

- **Fragment Communication:**
  - **Challenge:** Passing data between fragments and ensuring persistence.
  - **Solution:** I used `SharedPreferences` to save the user data in one fragment and retrieve it in another, ensuring that the preferences persist even after the app is closed and reopened.

- **UI Updates After Saving Preferences:**
  - **Challenge:** The changes made in `UserSettingsFragment` were not immediately visible in `ProfileViewFragment` when switching fragments.
  - **Solution:** I ensured that the preferences are loaded each time the `ProfileViewFragment` is created by placing the data retrieval logic inside the `onCreateView()` method.

- **Fragment Navigation and Layout Constraints:**
  - **Challenge:** Ensuring that the `BottomNavigationView` remains at the bottom and the fragments adjust to the screen size properly.
  - **Solution:** I used a `CoordinatorLayout` to wrap the layout, ensuring the bottom navigation stays fixed while the fragments adjust accordingly.

- **SharedPreferences Data Validation:**
  - **Challenge:** Ensuring that user input was valid before saving it in `SharedPreferences`.
  - **Solution:** I added validation checks to ensure that the username and email fields are not empty before saving the data. If the validation fails, an error message is displayed.

## Conclusion

This app successfully demonstrates how to use **SharedPreferences** for storing persistent user preferences and how to implement navigation between multiple fragments. Despite facing challenges in managing fragment communication and UI updates, I overcame these issues by leveraging Android components like `SharedPreferences`, `CoordinatorLayout`, and `FragmentTransaction`.

