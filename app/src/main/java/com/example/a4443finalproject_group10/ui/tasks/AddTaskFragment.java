package com.example.a4443finalproject_group10.ui.tasks;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a4443finalproject_group10.R;

public class AddTaskFragment extends Fragment {

    public AddTaskFragment() {
        super(R.layout.fragment_add_task);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Shared TaskViewModel
        TaskViewModel taskViewModel = new ViewModelProvider(requireActivity())
                .get(TaskViewModel.class);

        // Input field and save button
        EditText input = view.findViewById(R.id.taskInput);
        EditText description = view.findViewById(R.id.taskDescription);
        Button save = view.findViewById(R.id.saveButton);

        // Save new task through ViewModel and return to task list
        save.setOnClickListener(v -> {
            String text = input.getText().toString().trim();
            String details = description.getText().toString().trim();
            if (!text.isEmpty()) {
                taskViewModel.addTask(text, details);
            }
            NavHostFragment.findNavController(this).popBackStack();
        });
    }
}
