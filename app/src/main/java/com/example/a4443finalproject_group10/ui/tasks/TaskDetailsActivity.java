package com.example.a4443finalproject_group10.ui.tasks;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a4443finalproject_group10.R;

public class TaskDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_TASK_NAME = "task_name";
    public static final String EXTRA_TASK_DETAILS = "task_details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_screen);

        TextView taskName = findViewById(R.id.taskName);
        TextView taskDetails = findViewById(R.id.taskDetails);
        Button btnReturn = findViewById(R.id.returnButton);

        String createdTaskName = getIntent().getStringExtra(EXTRA_TASK_NAME);
        String createdTaskDetails = getIntent().getStringExtra(EXTRA_TASK_DETAILS);

        taskName.setText(createdTaskName != null ? createdTaskName : "No name");
        taskDetails.setText(createdTaskDetails != null ? createdTaskDetails : "No details");

        btnReturn.setOnClickListener(v -> finish());
    }
}
