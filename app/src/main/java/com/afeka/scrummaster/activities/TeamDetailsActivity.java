package com.afeka.scrummaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.data.Team;
import com.afeka.scrummaster.layout.Task;
import com.afeka.scrummaster.logic.TaskService;
import com.afeka.scrummaster.logic.TeamService;
import com.afeka.scrummaster.logic.UserService;
import com.google.android.gms.tasks.OnSuccessListener;

public class TeamDetailsActivity extends AppCompatActivity {
    private Team team;
    private UserService userService;
    private TeamService teamService;
    private TextView teamNameTextView;
    private Button joinTeamButton;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        userService = UserService.getInstance(this);
        teamService = new TeamService();
        handler = new Handler();

        if (savedInstanceState == null) {
            team = (Team) getIntent().getSerializableExtra("team");
            Log.e("TaskDetailsActivity", team.toString());
        }
        getSupportActionBar().setTitle("Team Details");

        teamNameTextView = findViewById(R.id.teamDetailsName);
        teamNameTextView.setText(team.getName());
        joinTeamButton = findViewById(R.id.joinTeamButton);
        joinTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamService.joinTeam(
                        userService.currentUser().getEmail(),
                        team,
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
                        }
                );
            }
        });
    }
}