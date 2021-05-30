package com.afeka.scrummaster.layout;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Task implements Serializable {
    private String taskId;
    private String creator;
    private String name;
    private String description;
    private String teamId;
    private Date createdAt;
    private TaskStatus taskStatus;
    private List<String> usersAssigned;

    public Task(String taskId, String creator, String name, String description, String teamId, Date createdAt, TaskStatus taskStatus, List<String> usersAssigned) {
        this.taskId = taskId;
        this.creator = creator;
        this.name = name;
        this.description = description;
        this.teamId = teamId;
        this.createdAt = createdAt;
        this.taskStatus = taskStatus;
        this.usersAssigned = usersAssigned;
    }

    public Task(String creator, String name, String description, String teamId, Date createdAt, TaskStatus taskStatus, List<String> usersAssigned) {
        this.creator = creator;
        this.name = name;
        this.description = description;
        this.teamId = teamId;
        this.createdAt = createdAt;
        this.taskStatus = taskStatus;
        this.usersAssigned = usersAssigned;
    }

    public Task() {
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<String> getUsersAssigned() {
        return usersAssigned;
    }

    public void setUsersAssigned(List<String> usersAssigned) {
        this.usersAssigned = usersAssigned;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", creator='" + creator + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", teamId='" + teamId + '\'' +
                ", createdAt=" + createdAt +
                ", taskStatus=" + taskStatus +
                ", usersAssigned=" + usersAssigned +
                '}';
    }
}
