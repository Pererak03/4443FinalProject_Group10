package com.example.a4443finalproject_group10;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


public class AddTaskFragment extends Fragment {

    // Use the layout for adding a new task
    public AddTaskFragment() {
        super(R.layout.fragment_add_task);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Input field and save button
        EditText input = view.findViewById(R.id.taskInput);
        Button save = view.findViewById(R.id.saveButton);

        // Save new task and return to task list
        save.setOnClickListener(v -> {
            String text = input.getText().toString().trim();
            if (!text.isEmpty()) {
                TaskRepo.addTask(requireContext(), text);
            }
            NavHostFragment.findNavController(this).popBackStack();
        });
    }
}
