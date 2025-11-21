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

    public AddTaskFragment() {
        super(R.layout.fragment_add_task);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText input = view.findViewById(R.id.taskInput);
        Button save = view.findViewById(R.id.saveButton);

        save.setOnClickListener(v -> {
            String text = input.getText().toString().trim();
            if (!text.isEmpty()) {
                TaskRepo.addTask(text);
            }

            NavHostFragment.findNavController(this).popBackStack();
        });
    }
}
