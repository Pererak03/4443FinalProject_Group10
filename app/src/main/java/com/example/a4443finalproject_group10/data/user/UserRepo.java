package com.example.a4443finalproject_group10.data.user;

import android.content.Context;

import com.example.a4443finalproject_group10.data.db.AppDatabase;

// Repository for user account operations
public class UserRepo {

    // Access the User DAO
    private static UserDao getDao(Context context) {
        return AppDatabase.getInstance(context).userDao();
    }

    // Register a new user if the email is not already taken
    public static User register(Context context, String email, String password) {
        UserDao dao = getDao(context);
        User existing = dao.findByEmail(email);
        if (existing != null) {
            return null; // email already in use
        }
        User user = new User(email, password);
        long id = dao.insert(user);
        user.id = (int) id;
        return user;
    }

    // Attempt login using email and password
    public static User login(Context context, String email, String password) {
        return getDao(context).login(email, password);
    }
}
