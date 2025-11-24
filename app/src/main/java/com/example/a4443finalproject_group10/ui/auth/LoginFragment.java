package com.example.a4443finalproject_group10.ui.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a4443finalproject_group10.R;
import com.example.a4443finalproject_group10.util.SessionManager;
import com.example.a4443finalproject_group10.data.user.User;
import com.example.a4443finalproject_group10.data.user.UserRepo;

public class LoginFragment extends Fragment {

    // Uses the login layout
    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // UI references
        EditText emailInput = view.findViewById(R.id.emailInput);
        EditText passwordInput = view.findViewById(R.id.passwordInput);
        Button loginButton = view.findViewById(R.id.loginButton);
        Button toSignupButton = view.findViewById(R.id.toSignupButton);

        // Attempt login
        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();

            User user = UserRepo.login(requireContext(), email, password);

            if (user != null) {
                // Store active user and move to task list
                SessionManager.setCurrentUserId(user.id);
                NavHostFragment.findNavController(this)
                        .navigate(R.id.taskListFragment);
            } else {
                Toast.makeText(requireContext(),
                        "Invalid email or password",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Go to sign-up screen
        toSignupButton.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.signUpFragment));
    }
}
