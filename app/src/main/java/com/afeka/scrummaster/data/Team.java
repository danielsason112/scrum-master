package com.afeka.scrummaster.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private String name;
    private List<String> members;

    public Team() {
    }

    public Team(String name, List<String> members) {
        this.name = name;
        this.members = members;
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

    public void addMember(String userId) {
        if (this.members == null) {
            this.members = new ArrayList<>();
        }
        this.members.add(userId);
    }

    @Override
    public String toString() {
        return "Team{" +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
}
