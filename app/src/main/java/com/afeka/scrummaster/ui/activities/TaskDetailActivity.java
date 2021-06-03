package com.afeka.scrummaster.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.afeka.scrummaster.interfaces.NavigationHost;
import com.afeka.scrummaster.R;
import com.afeka.scrummaster.ui.fragments.TaskDetailFragment;
import com.afeka.scrummaster.layout.Task;

public class TaskDetailActivity extends AppCompatActivity implements NavigationHost {
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        task = (Task)getIntent().getSerializableExtra("task");

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.task__detail_container, new TaskDetailFragment());
            transaction.commit();
        }
    }

    public Task getTask() {
        return task;
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.task__detail_container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}