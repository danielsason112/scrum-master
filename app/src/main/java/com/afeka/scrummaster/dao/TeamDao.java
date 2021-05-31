package com.afeka.scrummaster.dao;

import androidx.annotation.NonNull;

import com.afeka.scrummaster.data.Team;
import com.afeka.scrummaster.error.TeamExistsException;
import com.afeka.scrummaster.interfaces.OnTeamResListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TeamDao {
    private final String TEAM_DB_REF = "teams";
    private FirebaseDatabase db;

    public TeamDao() {
        db = FirebaseDatabase.getInstance();
    }

    public void create(String name, String creatorId, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
        DatabaseReference dbRef = db.getReference(TEAM_DB_REF);
        dbRef.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    store(name, creatorId, onSuccessListener, onFailureListener);
                } else {
                    onFailureListener.onFailure(new TeamExistsException());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public void joinTeam(String userId, Team team, OnSuccessListener listener) {
        DatabaseReference dbRef = db.getReference(TEAM_DB_REF);
        team.addMember(userId);
        dbRef.child(team.getName()).setValue(team)
        .addOnSuccessListener(listener);
    }

    private void store(String name, String creatorId, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
        DatabaseReference dbRef = db.getReference(TEAM_DB_REF);

        List<String> members = new ArrayList<String>();
        members.add(creatorId);
        Team team = new Team(name, members);
        dbRef.child(name)
                .setValue(team)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void findAll(OnTeamResListener listener) {
        DatabaseReference dbRef = db.getReference(TEAM_DB_REF);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                List<Team> teams = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Team team = postSnapshot.getValue(Team.class);
                    teams.add(team);
                }
                listener.onSuccess(teams);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                listener.onFailure(new Exception(error.getMessage()));
            }
        });
    }
}
