package com.example.a4443finalproject_group10;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * Shows the list of tasks for the logged-in user.
 */
public class TaskListFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton addTaskFab;
    private TaskListAdapter adapter;
    private TaskViewModel taskViewModel;

    public TaskListFragment() {
        super(R.layout.fragment_task_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.taskRecycler);
        addTaskFab = view.findViewById(R.id.addTaskFab);

        // Shared ViewModel for task data
        taskViewModel = new ViewModelProvider(requireActivity())
                .get(TaskViewModel.class);

        // Pass ViewModel into adapter
        adapter = new TaskListAdapter(new ArrayList<>(), taskViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Always reload tasks for the current user when this screen is shown
        taskViewModel.loadTasks();

        // Observe task updates
        taskViewModel.getTasks().observe(getViewLifecycleOwner(), tasks ->
                adapter.setTasks(tasks)
        );

        // Navigate to AddTaskFragment
        addTaskFab.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.addTaskFragment)
        );
    }
}
