package com.example.a4443finalproject_group10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private final List<Task> tasks;

    public TaskListAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskText;

        public TaskViewHolder(View itemView) {
            super(itemView);
            taskText = itemView.findViewById(R.id.taskText);
        }
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

        holder.itemView.setOnClickListener(v -> {
            task.toggleCompleted();
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
