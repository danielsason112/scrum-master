package com.afeka.scrummaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.logic.TeamService;
import com.afeka.scrummaster.logic.UserService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

public class AddTeamActivity extends AppCompatActivity {
    private EditText teamNameEditText;
    private Button saveTeamButton;
    private Handler handler;
    private UserService userService;
    private TeamService teamService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        userService = UserService.getInstance(this);
        teamService = new TeamService();
        handler = new Handler();

        teamNameEditText = findViewById(R.id.teamNameEditText);
        saveTeamButton = findViewById(R.id.saveTeamButton);
        saveTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamService.createTeam(
                        teamNameEditText.getText().toString(),
                        userService.currentUser().getEmail(),
                        new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                });
                            }
                        },
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {

                            }
                        }
                );
            }
        });

        getSupportActionBar().setTitle("Add Team");

    }
}