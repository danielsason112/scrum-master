package com.afeka.scrummaster.fragments;

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

import com.afeka.scrummaster.R;
import com.afeka.scrummaster.activities.BoardActivity;
import com.afeka.scrummaster.activities.TeamDetailsActivity;
import com.afeka.scrummaster.adapters.TeamsListAdapter;
import com.afeka.scrummaster.data.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TeamsListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<Team> teams;
    private Context context;
    private boolean isMyTeams;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TeamsListFragment() {
        this.teams = new ArrayList<Team>();
    }

    public TeamsListFragment(List<Team> teams, Context context, boolean isMyTeams) {
        this.context = context;
        this.teams = teams;
        this.isMyTeams = isMyTeams;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TeamsListFragment newInstance(int columnCount, List<Team> teamsList, Context context, boolean isMyTeams) {
        TeamsListFragment fragment = new TeamsListFragment(teamsList, context, isMyTeams);
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
            recyclerView.setAdapter(new TeamsListAdapter(teams, new TeamsListAdapter.onTeamClickListener() {
                @Override
                public void onTeamClick(Team team) {
                    if (isMyTeams) {
                        Intent intent = new Intent(context, BoardActivity.class);
                        intent.putExtra("team", team);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, TeamDetailsActivity.class);
                        intent.putExtra("team", team);
                        startActivity(intent);
                    }
                }
            }));
        }
        return view;
    }
}