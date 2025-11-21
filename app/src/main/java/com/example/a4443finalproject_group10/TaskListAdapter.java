package com.example.a4443finalproject_group10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// RecyclerView adapter for displaying tasks
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private final TaskViewModel viewModel;

    public TaskListAdapter(List<Task> tasks, TaskViewModel viewModel) {
        this.tasks = tasks;
        this.viewModel = viewModel;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
        }
    }

    // Allow updating the list from ViewModel
    public void setTasks(List<Task> newTasks) {
        this.tasks = newTasks;
        notifyDataSetChanged();
    }

    @Override
    public TaskListAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.taskText.setText(task.getDescription());

        holder.taskText.setAlpha(task.isCompleted() ? 0.3f : 1f);

        // Tap to toggle completion
        holder.itemView.setOnClickListener(v -> viewModel.toggleCompleted(task));

        // Long-press to Edit/Delete
        holder.itemView.setOnLongClickListener(v -> {
            showEditDeleteDialog(v, task);
            return true;
        });
    }

    private void showEditDeleteDialog(View view, Task task) {
        CharSequence[] options = {"Edit", "Delete"};

        new AlertDialog.Builder(view.getContext())
                .setTitle("Task options")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        showEditDialog(view, task);
                    } else if (which == 1) {
                        showDeleteConfirmDialog(view, task);
                    }
                })
                .show();
    }

    private void showEditDialog(View view, Task task) {
        EditText input = new EditText(view.getContext());
        input.setText(task.getDescription());
        input.setSelection(input.getText().length());

        new AlertDialog.Builder(view.getContext())
                .setTitle("Edit task")
                .setView(input)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newText = input.getText().toString().trim();
                    if (!newText.isEmpty()) {
                        viewModel.updateTask(task, newText);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showDeleteConfirmDialog(View view, Task task) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("Delete task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", (dialog, which) -> viewModel.deleteTask(task))
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return tasks != null ? tasks.size() : 0;
    }
}
