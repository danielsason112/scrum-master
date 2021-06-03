package com.afeka.scrummaster.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.interfaces.ResponseListener;
import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.layout.TaskStatus;
import com.afeka.scrummaster.logic.TaskService;
import com.afeka.scrummaster.logic.TeamService;
import com.afeka.scrummaster.logic.UserService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.Date;

public class NewTaskActivity extends AppCompatActivity {
    private Handler handler;
    private Context self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        handler = new Handler();
        self = this;

        String teamId = getIntent().getStringExtra("teamId");

        final TextView taskNameEditText = findViewById(R.id.task_name_edit_text);
        final TextView descriptionEditText = findViewById(R.id.task_description_edit_text);
        final RadioGroup taskStatusRadioGroup = findViewById(R.id.radioGroup);
        findViewById(R.id.add_task_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = taskNameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                int selectedId = taskStatusRadioGroup.getCheckedRadioButtonId();
                RadioButton selected = findViewById(taskStatusRadioGroup.getCheckedRadioButtonId());
                selected = (RadioButton) findViewById(selectedId);
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

                try {
                    TaskService.getInstance(self)
                            .createTask(new Task(
                                            UserService.getInstance(self).currentUser().getEmail(),
                                            name,
                                            description,
                                            teamId,
                                            new Date(),
                                            taskStatus,
                                            null
                                    ),
                                    new ResponseListener<Task>() {

                                        @Override
                                        public void onRes(Task res) {
                                            Log.e("ADD_TASK", "SUCCESS");
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