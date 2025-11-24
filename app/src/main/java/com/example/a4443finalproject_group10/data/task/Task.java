package com.example.a4443finalproject_group10.data.task;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.a4443finalproject_group10.data.user.User;

@Entity(
        tableName = "tasks",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("userId")}
)
public class Task {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // make these public so Room can read/write them directly
    public String description;
    public boolean completed;
    public int userId;
    public String details;

    // constructor used when creating a new task
    public Task(String description, String details, int userId) {
        this.description = description;
        this.details = details;
        this.userId = userId;
        this.completed = false;
    }

    // optional getters
    public String getDescription() { return description; }
    public String getDetails() { return details; }
    public boolean isCompleted()    { return completed; }

    public void toggleCompleted() {
        this.completed = !this.completed;
    }
}
