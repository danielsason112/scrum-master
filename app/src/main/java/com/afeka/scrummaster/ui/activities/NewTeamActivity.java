package com.afeka.scrummaster.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.logic.TeamService;
import com.afeka.scrummaster.logic.UserService;

public class NewTeamActivity extends AppCompatActivity {
    private Handler handler;
    private Context self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);

        handler = new Handler();
        self = this;

        final String creator = UserService.getInstance(this).currentUser().getEmail();

        EditText teamNameEditText = findViewById(R.id.team_name_edit_text);
        findViewById(R.id.add_team_button).setOnClickListener(view -> new TeamService().createTeam(
                teamNameEditText.getText().toString(),
                creator,
                o -> handler.post(() -> finish()),
                e -> {

                }
        ));
    }
}