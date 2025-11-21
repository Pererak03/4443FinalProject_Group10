package com.example.a4443finalproject_group10;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

// ViewModel for task data
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

    // Load tasks from repository
    public void loadTasks() {
        List<Task> list = TaskRepo.getTasks(getApplication());
        tasks.setValue(list);
    }

    // Add a new task and refresh list
    public void addTask(String description) {
        TaskRepo.addTask(getApplication(), description);
        loadTasks();
    }
}
