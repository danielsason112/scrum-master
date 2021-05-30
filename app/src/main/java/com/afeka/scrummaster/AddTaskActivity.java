package com.afeka.scrummaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.layout.TaskStatus;
import com.afeka.scrummaster.logic.TaskService;
import com.afeka.scrummaster.logic.UserService;

import org.json.JSONException;

import java.io.IOException;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    private EditText taskNameEditText;
    private EditText descriptionEditText;
    private RadioGroup taskStatusRadioGroup;
    private RadioButton taskStatusRadioButton;
    private Button addTaskButton;
    private Context self;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        this.self = this;
        this.handler = new Handler();

        getSupportActionBar().setTitle("Add Task");

        this.taskNameEditText = findViewById(R.id.taskNameEditText);
        this.descriptionEditText = findViewById(R.id.descriptionEditText);
        this.taskStatusRadioGroup = findViewById(R.id.taskStatusRadioGroup);
        this.addTaskButton = findViewById(R.id.saveTaskButton);

        this.addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = taskNameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                int selectedId = taskStatusRadioGroup.getCheckedRadioButtonId();
                taskStatusRadioButton = (RadioButton) findViewById(selectedId);
                String status = taskStatusRadioButton.getText().toString();
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

                try {
                    TaskService.getInstance(self)
                            .createTask(new Task(
                                    UserService.getInstance(self).currentUser().getEmail(),
                                    name,
                                    description,
                                    "Scrum Master",
                                    new Date(),
                                    taskStatus,
                                    null
                            ),
                                    new ResponseListener<Task>() {

                                        @Override
                                        public void onRes(Task res) {
                                            Log.e("ADD_TASK", "SUCCESS");
                                            Log.e("ADD_TASK", res.toString());
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    finish();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Log.e("ADD_TASK", "ERROR");

                                        }
                                    });
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}