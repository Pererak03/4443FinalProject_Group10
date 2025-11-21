package com.example.a4443finalproject_group10;

import android.content.Context;

import java.util.List;

// Repository for task-related database operations
public class TaskRepo {

    // Access the Task DAO
    private static TaskDao getDao(Context context) {
        return AppDatabase.getInstance(context).taskDao();
    }

    // Get tasks for the logged-in user
    public static List<Task> getTasks(Context context) {
        int userId = SessionManager.getCurrentUserId();
        if (userId == -1) return java.util.Collections.emptyList();
        return getDao(context).getTasksForUser(userId);
    }

    // Add a new task for the logged-in user
    public static void addTask(Context context, String description) {
        int userId = SessionManager.getCurrentUserId();
        if (userId == -1) return;
        Task task = new Task(description, userId);
        getDao(context).insert(task);
    }
}
