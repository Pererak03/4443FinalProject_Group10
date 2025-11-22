package com.example.a4443finalproject_group10;

// Tracks the currently logged-in user for the session
public class SessionManager {

    // -1 means no user is logged in
    private static int currentUserId = -1;

    private static InputMode currentMode = InputMode.BUTTON;

    // Store active user ID
    public static void setCurrentUserId(int id) {
        currentUserId = id;
    }

    // Retrieve active user ID
    public static int getCurrentUserId() {
        return currentUserId;
    }

    // Check login state
    public static boolean isLoggedIn() {
        return currentUserId != -1;
    }

    // Clear session
    public static void logout() {
        currentUserId = -1;
    }

    public static void setInputMode(InputMode mode) {
        currentMode = mode;
    }

    public static InputMode getInputMode() {
        return currentMode;
    }

}
