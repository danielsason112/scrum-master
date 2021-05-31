package com.afeka.scrummaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.interfaces.ResponseListener;
import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.layout.TaskStatus;
import com.afeka.scrummaster.logic.TaskService;
import com.afeka.scrummaster.logic.UserService;

import org.json.JSONException;

import java.io.IOException;

public class TaskDetailsActivity extends AppCompatActivity {
    private Task task;
    private UserService userService;
    private TaskService taskService;
    private TextView taskNameTextView;
    private TextView taskDescriptionTextView;
    private TextView taskStatusTextView;
    private TextView taskCreatedAtTextView;
    private Button editTaskButton;
    private EditText editNameInput;
    private EditText editDescriptionInput;
    private RadioGroup statusRadioGroup;
    private RadioButton statusRadioButton;
    private Button updateTaskButton;
    private ViewGroup detailsLayout;
    private ViewGroup inputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        userService = UserService.getInstance(this);
        taskService = TaskService.getInstance(this);

        if (savedInstanceState == null) {
            task = (Task) getIntent().getSerializableExtra("task");
            Log.e("TaskDetailsActivity", task.toString());
        }
        getSupportActionBar().setTitle("Task Details");

        taskNameTextView = findViewById(R.id.taskDetailsName);
        taskDescriptionTextView = findViewById(R.id.taskDetailsDescription);
        taskStatusTextView = findViewById(R.id.taskDetailsStatus);
        taskCreatedAtTextView = findViewById(R.id.taskDetailsCreatedAt);
        editTaskButton = findViewById(R.id.editTaskButton);
        editNameInput = findViewById(R.id.editTaskNameInput);
        editDescriptionInput = findViewById(R.id.editDescriptionInput);
        statusRadioGroup = findViewById(R.id.editTaskStatus);
        detailsLayout = findViewById(R.id.taskDetailsLayout);
        inputLayout = findViewById(R.id.editInputsLayout);
        updateTaskButton = findViewById(R.id.updateTaskButton);

        if (task.getCreator().equals(userService.currentUser().getEmail())) {
            editTaskButton.setVisibility(View.VISIBLE);
            editTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    detailsLayout.setVisibility(View.GONE);
                    inputLayout.setVisibility(View.VISIBLE);
                    editTaskButton.setVisibility(View.GONE);
                    updateTaskButton.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        taskNameTextView.setText("Name: " + task.getName());
        taskDescriptionTextView.setText("Description: " + task.getDescription());
        taskStatusTextView.setText("Status: " + task.getTaskStatus().name());
        taskCreatedAtTextView.setText("Created At: " + task.getCreatedAt().toString());
        editNameInput.setText(task.getName());
        editDescriptionInput.setText(task.getDescription());
        statusRadioGroup.check(getStatusRadioButtonId(task.getTaskStatus()));

        updateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setName(editNameInput.getText().toString());
                task.setDescription(editDescriptionInput.getText().toString());
                int selectedId = statusRadioGroup.getCheckedRadioButtonId();
                statusRadioButton = (RadioButton) findViewById(selectedId);
                String StringStatus = statusRadioButton.getText().toString();
                TaskStatus taskStatus = TaskStatus.BACKLOG;
                switch (StringStatus) {
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
                    taskService.updateTask(task, new ResponseListener<Task>() {
                        @Override
                        public void onRes(Task res) {

                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                taskNameTextView.setText(task.getName());
                taskDescriptionTextView.setText(task.getDescription());
                taskStatusTextView.setText(task.getTaskStatus().name());

                detailsLayout.setVisibility(View.VISIBLE);
                inputLayout.setVisibility(View.GONE);
                editTaskButton.setVisibility(View.VISIBLE);
                updateTaskButton.setVisibility(View.GONE);
            }
        });


    }

    private int getStatusRadioButtonId(TaskStatus taskStatus) {
        switch (taskStatus) {
            case BACKLOG:
                return R.id.editTaskBaclogRadioButton;
            case TODO:
                return R.id.editTaskToDoRadioButton;
            case IN_PROGRESS:
                return R.id.editTaskInProgressRadioButton;
            case REVIEW:
                return R.id.editTaskReviewRadioButton;
            case DONE:
                return R.id.editTaskDoneRadioButton;
        }
        return R.id.editTaskBaclogRadioButton;
    }
}