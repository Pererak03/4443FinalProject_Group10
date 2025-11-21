package com.example.a4443finalproject_group10;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

// DAO for accessing User records
@Dao
public interface UserDao {

    // Create a new user account
    @Insert
    long insert(User user);

    // Check if an email already exists
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User findByEmail(String email);

    // Validate login credentials
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);
}
