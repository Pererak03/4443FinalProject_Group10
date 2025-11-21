package com.example.a4443finalproject_group10;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Room entity representing a registered user
@Entity(tableName = "users")
public class User {

    // Auto-generated user ID
    @PrimaryKey(autoGenerate = true)
    public int id;

    // Login credentials
    public String email;
    public String password;

    // Constructor used when creating a new user
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
