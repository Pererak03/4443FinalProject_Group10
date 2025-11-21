package com.example.a4443finalproject_group10;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4443finalproject_group10.TaskRepo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * This Fragment is responsible for showing the list of tasks.
 * It contains a RecyclerView and a FloatingActionButton ("+")
 * that navigates the user to AddTaskFragment.
 */
public class TaskListFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton addTaskFab;

    // tells Fragment which XML layout to use.
    public TaskListFragment() {
        super(R.layout.fragment_task_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.taskRecycler);
        addTaskFab = view.findViewById(R.id.addTaskFab);

        // Create the RecyclerView Adapter and give it the list of tasks from the repository
        TaskListAdapter adapter = new TaskListAdapter(TaskRepo.getTasks(requireContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Navigation controller handles moving between fragments
        addTaskFab.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.addTaskFragment);
        });
    }
}


