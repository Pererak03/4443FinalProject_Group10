package com.example.a4443finalproject_group10.ui.tasks;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a4443finalproject_group10.util.InputMode;
import com.example.a4443finalproject_group10.R;
import com.example.a4443finalproject_group10.util.SessionManager;
import com.example.a4443finalproject_group10.data.task.Task;

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
        TextView taskDetails;
        LinearLayout actionRow;
        ImageButton btnComplete;
        ImageButton btnEdit;
        ImageButton btnDelete;
        ImageButton btnDetails;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
            taskDetails = itemView.findViewById(R.id.taskDescription);
            actionRow = itemView.findViewById(R.id.actionRow);
            btnComplete = itemView.findViewById(R.id.btnComplete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDetails = itemView.findViewById(R.id.btnDetails);
        }
    }

    // Update list from ViewModel
    public void setTasks(List<Task> newTasks) {
        this.tasks = newTasks;
        notifyDataSetChanged();
    }

    // Used by swipe helper
    public Task getTaskAt(int position) {
        return tasks != null && position >= 0 && position < tasks.size()
                ? tasks.get(position)
                : null;
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
        holder.taskDetails.setText(task.getDetails());
        holder.taskText.setAlpha(task.isCompleted() ? 0.3f : 1f);

        InputMode mode = SessionManager.getInputMode();

        if (mode == InputMode.BUTTON) {
            // Buttons visible, no gesture behavior on item root
            holder.actionRow.setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(null);

            holder.itemView.setOnLongClickListener(null);

            holder.btnComplete.setOnClickListener(v -> viewModel.toggleCompleted(task));

            holder.btnEdit.setOnClickListener(v -> showEditDialog(v, task));

            holder.btnDelete.setOnClickListener(v -> showDeleteConfirmDialog(v, task));

            holder.btnDetails.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), TaskDetailsActivity.class);
                intent.putExtra(TaskDetailsActivity.EXTRA_TASK_NAME, task.getDescription());
                intent.putExtra(TaskDetailsActivity.EXTRA_TASK_DETAILS, task.getDetails());
                v.getContext().startActivity(intent);
            });

        } else { // GESTURE mode
            // Hide buttons, use tap / long-press / swipe
            holder.actionRow.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(v -> viewModel.toggleCompleted(task));

            holder.itemView.setOnLongClickListener(v -> {
                showEditDeleteDialog(v, task);
                return true;
            });

            // In gesture mode, swipe delete is handled by ItemTouchHelper in the fragment
        }
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
        LinearLayout layout = new LinearLayout(view.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        EditText input = new EditText(view.getContext());
        input.setText(task.getDescription());
        input.setSelection(input.getText().length());
        layout.addView(input);

        EditText inputDetails = new EditText(view.getContext());
        inputDetails.setText(task.getDetails());
        inputDetails.setSelection(inputDetails.getText().length());
        layout.addView(inputDetails);

        new AlertDialog.Builder(view.getContext())
                .setTitle("Edit task")
                .setView(layout)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newText = input.getText().toString().trim();
                    String newDetails = inputDetails.getText().toString().trim();
                    if (!newText.isEmpty()) {
                        viewModel.updateTask(task, newText, newDetails);
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
