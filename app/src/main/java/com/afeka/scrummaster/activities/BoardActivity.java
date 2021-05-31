package com.afeka.scrummaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.interfaces.ResponseListener;
import com.afeka.scrummaster.fragments.TasksListFragment;
import com.afeka.scrummaster.data.Team;
import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.logic.TaskService;
import com.afeka.scrummaster.logic.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    private UserService userService;
    private TaskService taskService;
    private Context self;
    private FloatingActionButton newTaskButton;
    private Team team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        this.userService = UserService.getInstance(this);
        this.taskService = TaskService.getInstance(this);
        self = this;

        FirebaseApp.initializeApp(getApplicationContext());

        getSupportActionBar().setTitle("Kanban Board");

        if (savedInstanceState == null) {
            team = (Team) getIntent().getSerializableExtra("team");

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
                intent.putExtra("teamId", team.getName());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (this.userService.currentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        if (team != null) {
            this.taskService.findByTeam(team.getName(), new ResponseListener<List<Task>>() {
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

//        this.taskService.findAll(new ResponseListener<List<Task>>() {
//            @Override
//            public void onRes(List<Task> res) {
//                Log.e("tasks", res.toString());
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                TasksListFragment fragment = TasksListFragment.newInstance(1, res, self);
//                transaction.replace(R.id.fragmentContainerView, fragment);
//                transaction.commit();
//
//            }
//
//            @Override
//            public void onError(Exception e) {
//
//            }
//        });
    }
}