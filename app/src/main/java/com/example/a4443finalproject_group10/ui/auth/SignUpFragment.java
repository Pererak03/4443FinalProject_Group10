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

public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        super(R.layout.fragment_signup);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText emailInput = view.findViewById(R.id.emailInput);
        EditText passwordInput = view.findViewById(R.id.passwordInput);
        EditText confirmInput = view.findViewById(R.id.confirmPasswordInput);
        Button signupButton = view.findViewById(R.id.signupButton);
        Button toLoginButton = view.findViewById(R.id.toLoginButton);

        signupButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString();
            String confirm = confirmInput.getText().toString();

            // Check for empty fields
            if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(requireContext(),
                        "All fields are required",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Email validation using Android built-in regex
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(),
                        "Please enter a valid email address",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Check matching passwords
            if (!password.equals(confirm)) {
                Toast.makeText(requireContext(),
                        "Passwords do not match",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Try registering user
            User newUser = UserRepo.register(requireContext(), email, password);

            if (newUser == null) {
                Toast.makeText(requireContext(),
                        "Email already in use",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Success: save session and go to task list
            SessionManager.setCurrentUserId(newUser.id);

            NavHostFragment.findNavController(this)
                    .navigate(R.id.taskListFragment);

        });

        // Back to login
        toLoginButton.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.loginFragment));
    }
}
