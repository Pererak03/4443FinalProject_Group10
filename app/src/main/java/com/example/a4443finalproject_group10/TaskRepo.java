package com.example.a4443finalproject_group10;

import com.example.a4443finalproject_group10.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskRepo {

    private static final List<Task> tasks = new ArrayList<>();

    public static List<Task> getTasks() {
        return tasks;
    }

    public static void addTask(String description) {
        tasks.add(new Task(description));
    }
}
