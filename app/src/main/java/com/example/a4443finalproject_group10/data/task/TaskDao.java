package com.example.a4443finalproject_group10.data.task;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

// DAO for accessing Task records in the Room database
@Dao
public interface TaskDao {

    // Get all tasks belonging to a specific user
    @Query("SELECT * FROM tasks WHERE userId = :userId")
    List<Task> getTasksForUser(int userId);

    // Add a new task
    @Insert
    void insert(Task task);

    // Update an existing task
    @Update
    void update(Task task);

    // Remove a task
    @Delete
    void delete(Task task);
}
