package com.example.a4443finalproject_group10.data.task;

import android.content.Context;

import com.example.a4443finalproject_group10.util.SessionManager;
import com.example.a4443finalproject_group10.data.db.AppDatabase;

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
    public static void addTask(Context context, String description, String details) {
        int userId = SessionManager.getCurrentUserId();
        if (userId == -1) return;
        Task task = new Task(description, details, userId);
        getDao(context).insert(task);
    }

    // Update an existing task
    public static void updateTask(Context context, Task task) {
        getDao(context).update(task);
    }

    // Delete a task
    public static void deleteTask(Context context, Task task) {
        getDao(context).delete(task);
    }

}
