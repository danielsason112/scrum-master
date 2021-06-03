package com.afeka.scrummaster.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.interfaces.ResponseListener;
import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.layout.TaskStatus;
import com.afeka.scrummaster.logic.TaskService;
import com.afeka.scrummaster.ui.activities.TaskDetailActivity;

import org.json.JSONException;

import java.io.IOException;

public class EditTaskFragment extends Fragment {
    private Task task;
    private Handler handler;

    public EditTaskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = ((TaskDetailActivity)getActivity()).getTask();
        handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        final TextView taskNameEditText = view.findViewById(R.id.task_name_edit_text);
        final TextView descriptionEditText = view.findViewById(R.id.task_description_edit_text);
        final RadioGroup taskStatusRadioGroup = view.findViewById(R.id.radioGroup);

        final Button doneButton = view.findViewById(R.id.done_button);


        taskNameEditText.setText(task.getName());
        descriptionEditText.setText(task.getDescription());
        taskStatusRadioGroup.check(getStatusRadioButtonId(task.getTaskStatus()));
        doneButton.setOnClickListener(view1 -> {
            task.setName(taskNameEditText.getText().toString());
            task.setDescription(descriptionEditText.getText().toString());
            RadioButton selected = taskStatusRadioGroup.findViewById(taskStatusRadioGroup.getCheckedRadioButtonId());
            String status = selected.getText().toString();
            TaskStatus taskStatus = TaskStatus.BACKLOG;
            switch (status) {
                case "Backlog":
                    taskStatus = TaskStatus.BACKLOG;
                    break;
                case "To Do":
                    taskStatus = TaskStatus.TODO;
                    break;
                case "In Progress":
                    taskStatus = TaskStatus.IN_PROGRESS;
                    break;
                case "Review":
                    taskStatus = TaskStatus.REVIEW;
                    break;
                case "Done":
                    taskStatus = TaskStatus.DONE;
                    break;
            }
            task.setTaskStatus(taskStatus);
            try {
                TaskService.getInstance(getContext()).updateTask(task, new ResponseListener<Task>() {
                    @Override
                    public void onRes(Task res) { }

                    @Override
                    public void onError(Exception e) { }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        task = ((TaskDetailActivity)getActivity()).getTask();
    }

    private int getStatusRadioButtonId(TaskStatus taskStatus) {
        switch (taskStatus) {
            case BACKLOG:
                return R.id.radio_button_backlog;
            case TODO:
                return R.id.radio_button_to_do;
            case IN_PROGRESS:
                return R.id.radio_button_in_progress;
            case REVIEW:
                return R.id.radio_button_review;
            case DONE:
                return R.id.radio_button_done;
        }
        return R.id.radio_button_backlog;
    }
}