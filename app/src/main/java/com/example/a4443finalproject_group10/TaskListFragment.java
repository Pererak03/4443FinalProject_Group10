package com.example.a4443finalproject_group10;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
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
    private ItemTouchHelper swipeHelper;

    public TaskListFragment() {
        super(R.layout.fragment_task_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.taskRecycler);
        addTaskFab = view.findViewById(R.id.addTaskFab);

        MaterialButtonToggleGroup modeToggleGroup = view.findViewById(R.id.modeToggleGroup);
        MaterialButton textModeBtn = view.findViewById(R.id.btnTextMode);
        MaterialButton gestureModeBtn = view.findViewById(R.id.btnGestureMode);

        taskViewModel = new ViewModelProvider(requireActivity())
                .get(TaskViewModel.class);

        adapter = new TaskListAdapter(new ArrayList<>(), taskViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Swipe helper (used in gesture mode, with confirmation)
        swipeHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Task task = adapter.getTaskAt(position);
                        if (task == null) {
                            adapter.notifyItemChanged(position);
                            return;
                        }

                        new AlertDialog.Builder(requireContext())
                                .setTitle("Delete task")
                                .setMessage("Are you sure you want to delete this task?")
                                .setPositiveButton("Delete", (dialog, which) ->
                                        taskViewModel.deleteTask(task))
                                .setNegativeButton("Cancel", (dialog, which) ->
                                        adapter.notifyItemChanged(position))
                                .setOnCancelListener(d ->
                                        adapter.notifyItemChanged(position))
                                .show();
                    }
                });

        // GestureDetector for double-tap on empty area (gesture mode only)
        GestureDetector gestureDetector = new GestureDetector(
                requireContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        if (SessionManager.getInputMode() != InputMode.GESTURE) {
                            return false;
                        }
                        // Only react if double-tap is not on a task item
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child == null) {
                            NavHostFragment.findNavController(TaskListFragment.this)
                                    .navigate(R.id.addTaskFragment);
                            return true;
                        }
                        return false;
                    }
                });

        recyclerView.setOnTouchListener((v, event) -> {
            if (SessionManager.getInputMode() == InputMode.GESTURE) {
                gestureDetector.onTouchEvent(event);
            }
            // false so scrolling / normal handling still works
            return false;
        });

        // Initial mode and toggle state
        InputMode currentMode = SessionManager.getInputMode();
        if (currentMode == InputMode.BUTTON) {
            modeToggleGroup.check(textModeBtn.getId());
            swipeHelper.attachToRecyclerView(null);
        } else {
            modeToggleGroup.check(gestureModeBtn.getId());
            swipeHelper.attachToRecyclerView(recyclerView);
        }
        updateModeButtonStyles(currentMode, requireContext(), textModeBtn, gestureModeBtn);

        // Mode change handling
        modeToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (!isChecked) return;

            InputMode newMode;
            if (checkedId == textModeBtn.getId()) {
                newMode = InputMode.BUTTON;
                SessionManager.setInputMode(InputMode.BUTTON);
                swipeHelper.attachToRecyclerView(null);
            } else {
                newMode = InputMode.GESTURE;
                SessionManager.setInputMode(InputMode.GESTURE);
                swipeHelper.attachToRecyclerView(recyclerView);
            }

            updateModeButtonStyles(newMode, requireContext(), textModeBtn, gestureModeBtn);

            taskViewModel.loadTasks();
            adapter.notifyDataSetChanged();
        });

        // Load tasks when fragment appears
        taskViewModel.loadTasks();

        taskViewModel.getTasks().observe(getViewLifecycleOwner(), tasks ->
                adapter.setTasks(tasks)
        );

        addTaskFab.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.addTaskFragment)
        );
    }

    private void updateModeButtonStyles(InputMode mode,
                                        Context context,
                                        MaterialButton textButton,
                                        MaterialButton gestureButton) {

        int primary = ContextCompat.getColor(context, R.color.black);
        int white = ContextCompat.getColor(context, android.R.color.white);
        int transparent = ContextCompat.getColor(context, android.R.color.transparent);

        if (mode == InputMode.BUTTON) {
            textButton.setBackgroundTintList(ColorStateList.valueOf(primary));
            textButton.setTextColor(white);

            gestureButton.setBackgroundTintList(ColorStateList.valueOf(transparent));
            gestureButton.setTextColor(primary);
        } else {
            gestureButton.setBackgroundTintList(ColorStateList.valueOf(primary));
            gestureButton.setTextColor(white);

            textButton.setBackgroundTintList(ColorStateList.valueOf(transparent));
            textButton.setTextColor(primary);
        }
    }
}
