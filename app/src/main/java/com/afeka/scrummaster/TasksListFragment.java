package com.afeka.scrummaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afeka.scrummaster.layout.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TasksListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<Task> tasks;
    private Context context;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TasksListFragment() {
        this.tasks = new ArrayList<Task>();
    }

    public TasksListFragment(List<Task> tasks, Context context) {
        this.context = context;
        this.tasks = tasks;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TasksListFragment newInstance(int columnCount, List<Task> taskList, Context context) {
        TasksListFragment fragment = new TasksListFragment(taskList, context);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new TasksListAdapter(tasks, new TasksListAdapter.onTaskClickListener() {
                @Override
                public void onTaskClick(Task task) {
                    Intent intent = new Intent(context, TaskDetailsActivity.class);
                    intent.putExtra("task", task);
                    startActivity(intent);
                }
            }));
        }
        return view;
    }
}