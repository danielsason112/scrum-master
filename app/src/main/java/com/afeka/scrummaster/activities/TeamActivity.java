package com.afeka.scrummaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.fragments.TeamsListFragment;
import com.afeka.scrummaster.data.Team;
import com.afeka.scrummaster.interfaces.OnTeamResListener;
import com.afeka.scrummaster.logic.TeamService;
import com.afeka.scrummaster.logic.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity {
    private UserService userService;
    private TeamService teamService;
    private Context self;
    private FloatingActionButton addTeamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        this.userService = UserService.getInstance(this);
        teamService = new TeamService();
        self = this;

        FirebaseApp.initializeApp(getApplicationContext());

        getSupportActionBar().setTitle("Teams List");

        addTeamButton = findViewById(R.id.addTeamButton);
        addTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self, AddTeamActivity.class);
                startActivity(intent);
            }
        });

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            TeamsListFragment fragment = TeamsListFragment.newInstance(1, new ArrayList<Team>(), this, true);
            transaction.replace(R.id.myTeamsFragment, fragment);
            TeamsListFragment fragment2 = TeamsListFragment.newInstance(1, new ArrayList<Team>(), this, false);
            transaction.replace(R.id.availableTeamsFragment, fragment2);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (this.userService.currentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        this.teamService.findAll(
                new OnTeamResListener() {
                    @Override
                    public void onSuccess(List<Team> teams) {
                        String userEmail = userService.currentUser().getEmail();
                        List<Team> myTeams = new ArrayList<>();
                        List<Team> availableTeams = new ArrayList<>();
                        for (Team t :
                                teams) {
                            if (t.getMembers().contains(userEmail)) {
                                myTeams.add(t);
                            } else {
                                availableTeams.add(t);
                            }
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        TeamsListFragment fragment = TeamsListFragment.newInstance(1, myTeams, self, true);
                        transaction.replace(R.id.myTeamsFragment, fragment);
                        TeamsListFragment fragment2 = TeamsListFragment.newInstance(1, availableTeams, self, false);
                        transaction.replace(R.id.availableTeamsFragment, fragment2);
                        transaction.commit();
                    }

                    @Override
                    public void onSuccess(Team team) {

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                }
        );
    }
}