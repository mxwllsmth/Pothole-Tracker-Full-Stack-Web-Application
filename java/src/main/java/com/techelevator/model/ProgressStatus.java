package com.techelevator.model;

public class ProgressStatus {

    //INSTANCE VARIABLES

    private Integer progressStatusId;
    private String progressStatusDesc;

    //GETTERS

    public Integer getProgressStatusId() {
        return progressStatusId;
    }

    public String getProgressStatusDesc() {
        return progressStatusDesc;
    }

    //SETTERS

    public void setProgressStatusId(Integer progressStatusId) {
        this.progressStatusId = progressStatusId;
    }

    public void setProgressStatusDesc(String progressStatusDesc) {
        this.progressStatusDesc = progressStatusDesc;
    }

    //CONSTRUCTORS

    public ProgressStatus() {};

    public ProgressStatus(Integer progressStatusId, String progressStatusDesc) {
        this.progressStatusId = progressStatusId;
        this.progressStatusDesc = progressStatusDesc;
    }
}
