package com.afeka.scrummaster.logic;

import com.afeka.scrummaster.interfaces.OnTeamResListener;
import com.afeka.scrummaster.dao.TeamDao;
import com.afeka.scrummaster.data.Team;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class TeamService {
    private TeamDao dao;

    public TeamService() {
        dao = new TeamDao();
    }

    public void createTeam(String name, String creatorId, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
        this.dao.create(name, creatorId, onSuccessListener, onFailureListener);
    }

    public void findAll(OnTeamResListener listener) {
        this.dao.findAll(listener);
    }

    public void joinTeam(String userId, Team team, OnSuccessListener listener) {
        this.dao.joinTeam(userId, team, listener);
    }
}
