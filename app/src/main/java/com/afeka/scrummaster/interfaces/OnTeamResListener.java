package com.afeka.scrummaster.interfaces;

import com.afeka.scrummaster.data.Team;

import java.util.List;

public interface OnTeamResListener {
    public void onSuccess(List<Team> teams);
    public void onSuccess(Team team);
    public void onFailure(Exception e);
}
