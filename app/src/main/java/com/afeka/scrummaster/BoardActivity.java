package com.afeka.scrummaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.logic.TaskService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    private TaskService taskService;
    private Context self;
    private FloatingActionButton newTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        this.taskService = TaskService.getInstance(this);
        self = this;

        getSupportActionBar().setTitle("Kanban Board");

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            TasksListFragment fragment = TasksListFragment.newInstance(1, new ArrayList<Task>(), this);
            transaction.replace(R.id.fragmentContainerView, fragment);
            transaction.commit();
        }

        this.newTaskButton = findViewById(R.id.newTaskButton);
        this.newTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self, AddTaskActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.taskService.findAll(new ResponseListener<List<Task>>() {
            @Override
            public void onRes(List<Task> res) {
                Log.e("tasks", res.toString());
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                TasksListFragment fragment = TasksListFragment.newInstance(1, res, self);
                transaction.replace(R.id.fragmentContainerView, fragment);
                transaction.commit();

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}