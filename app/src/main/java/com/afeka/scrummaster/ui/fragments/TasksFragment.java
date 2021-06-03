package com.afeka.scrummaster.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.ui.adapters.TasksAdapter;
import com.afeka.scrummaster.data.Team;
import com.afeka.scrummaster.interfaces.ResponseListener;
import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.layout.User;
import com.afeka.scrummaster.logic.TaskService;
import com.afeka.scrummaster.logic.UserService;
import com.afeka.scrummaster.ui.activities.NewTaskActivity;
import com.afeka.scrummaster.ui.activities.TaskDetailActivity;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.List;

public class TasksFragment extends Fragment {
    private Team team;

    public TasksFragment() {
    }

    public TasksFragment(Team team) {
        this.team = team;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        final CircularProgressIndicator progressIndicator = view.findViewById(R.id.tasks_progress_bar);

        UserService.getInstance(getContext()).findAll(new ResponseListener<List<User>>() {
            @Override
            public void onRes(List<User> res) {
                List<User> users = res;
                TaskService.getInstance(getContext()).findByTeam(team.getName(), new ResponseListener<List<Task>>() {
                    @Override
                    public void onRes(List<Task> res) {
                        RecyclerView tasksRV = view.findViewById(R.id.tasks_rc);
                        tasksRV.setLayoutManager(new LinearLayoutManager(getContext()));
                        progressIndicator.setVisibility(View.GONE);
                        tasksRV.setAdapter(new TasksAdapter(res, users, new TasksAdapter.onTaskClick() {
                            @Override
                            public void onClick(Task task) {
                                Intent intent = new Intent(getContext(), TaskDetailActivity.class);
                                intent.putExtra("task", task);
                                startActivity(intent);
                            }
                        }));
                    }

                    @Override
                    public void onError(Exception e) {
                        progressIndicator.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });

        view.findViewById(R.id.new_task_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewTaskActivity.class);
                intent.putExtra("teamId", team.getName());
                startActivity(intent);
            }
        });

        return view;
    }
}