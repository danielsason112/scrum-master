package com.afeka.scrummaster.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.ui.adapters.TeamsAdapter;
import com.afeka.scrummaster.data.Team;
import com.afeka.scrummaster.interfaces.OnTeamResListener;
import com.afeka.scrummaster.logic.TeamService;
import com.afeka.scrummaster.logic.UserService;
import com.afeka.scrummaster.ui.activities.MainActivity;
import com.afeka.scrummaster.ui.activities.NewTeamActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class TeamsFragment extends Fragment {
    private TeamService teamService;

    public TeamsFragment() {
        teamService = new TeamService();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        if (container != null) {
            container.removeAllViews();
        }

        view.findViewById(R.id.new_team_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setBoardTeam(null);
                startActivity(new Intent(getContext(), NewTeamActivity.class));
            }
        });

        teamService.findAll(new OnData(view));

        return view;
    }

    private void updateView(View view, List<Team> teams) {
        RecyclerView myTeamsRV = view.findViewById(R.id.my_teams_rc);
        RecyclerView otherTeamsRV = view.findViewById(R.id.other_teams_rc);
        CircularProgressIndicator loader = view.findViewById(R.id.teams_progress_bar);

        List<Team> myTeams = new ArrayList<>();
        List<Team> otherTeams = new ArrayList<>();
        for (Team t :
                teams) {
            boolean res = t.getMembers()
                    .contains(UserService.getInstance(getContext()).currentUser().getEmail()) ?
                    myTeams.add(t) : otherTeams.add(t);
        }

        myTeamsRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        otherTeamsRV.setLayoutManager(new LinearLayoutManager(view.getContext()));

        myTeamsRV.setAdapter(new TeamsAdapter(myTeams, true, new OnClickToBoard()));
        otherTeamsRV.setAdapter(new TeamsAdapter(otherTeams, false, new OnClickToJoin())
        );
        Log.e("TEAMS", "SUCCESS");
        loader.setVisibility(View.GONE);
    }

    private class OnData implements OnTeamResListener {
        private View view;

        private OnData(View view) {
            this.view = view;
        }

        @Override
        public void onSuccess(List<Team> teams) {
            updateView(view, teams);
        }

        @Override
        public void onSuccess(Team team) {

        }

        @Override
        public void onFailure(Exception e) {

        }
    }

    private class OnClickToJoin implements TeamsAdapter.onActionButtonClick {
        @Override
        public void onClick(Team team) {
            teamService.joinTeam(
                    UserService.getInstance(getContext())
                            .currentUser()
                            .getEmail(),
                    team,
                    new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            ((MainActivity)getActivity()).navigateTo(new TeamsFragment(), false);
                        }
                    }

            );
        }
    }

    private class OnClickToBoard implements TeamsAdapter.onActionButtonClick {

        @Override
        public void onClick(Team team) {
            ((MainActivity)getActivity()).setBoardTeam(team);
            ((MainActivity)getActivity()).navigateTo(new TasksFragment(team), true);
        }
    }
}