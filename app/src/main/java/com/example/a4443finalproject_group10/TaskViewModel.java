package com.example.a4443finalproject_group10;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

// ViewModel for task data (per logged-in user)
public class TaskViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Task>> tasks = new MutableLiveData<>();

    public TaskViewModel(@NonNull Application application) {
        super(application);
        loadTasks();
    }

    // Expose tasks as LiveData
    public LiveData<List<Task>> getTasks() {
        return tasks;
    }

    // Load tasks for current user
    public void loadTasks() {
        List<Task> list = TaskRepo.getTasks(getApplication());
        tasks.setValue(list);
    }

    // Add new task
    public void addTask(String description, String detail) {
        TaskRepo.addTask(getApplication(), description, detail);
        loadTasks();
    }

    // Toggle completion and persist
    public void toggleCompleted(Task task) {
        task.toggleCompleted();
        TaskRepo.updateTask(getApplication(), task);
        loadTasks();
    }

    // Update description and persist
    public void updateTask(Task task, String newDescription, String newDetails) {
        task.description = newDescription;
        task.details = newDetails;
        TaskRepo.updateTask(getApplication(), task);
        loadTasks();
    }

    // Delete task
    public void deleteTask(Task task) {
        TaskRepo.deleteTask(getApplication(), task);
        loadTasks();
    }
}
