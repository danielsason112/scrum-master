package com.afeka.scrummaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.afeka.scrummaster.layout.Task;

public class TaskDetailsActivity extends AppCompatActivity {
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        if (savedInstanceState == null) {
            task = (Task) getIntent().getSerializableExtra("task");
            Log.e("TaskDetailsActivity", task.toString());
        }
        getSupportActionBar().setTitle("Task Details");
    }
}