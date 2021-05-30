package com.afeka.scrummaster.data;

import java.util.List;

public class Team {
    private String teamId;
    private String name;
    private List<String> members;

    public Team() {
    }

    public Team(String teamId, String name, List<String> members) {
        this.teamId = teamId;
        this.name = name;
        this.members = members;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId='" + teamId + '\'' +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
}
