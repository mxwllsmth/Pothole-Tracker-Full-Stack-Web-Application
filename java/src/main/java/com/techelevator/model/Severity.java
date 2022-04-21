package com.techelevator.model;

public class Severity {

    //INSTANCE VARIABLES

    private Integer severityId;
    private String severityTypeDesc;

    //GETTERS

    public Integer getSeverityId() {
        return severityId;
    }

    public String getSeverityTypeDesc() {
        return severityTypeDesc;
    }

    //SETTERS

    public void setSeverityId(Integer severityId) {
        this.severityId = severityId;
    }

    public void setSeverityTypeDesc(String severityTypeDesc) {
        this.severityTypeDesc = severityTypeDesc;
    }

    //CONSTRUCTORS

    public Severity() {};

    public Severity(Integer severityId, String severityTypeDesc) {
        this.severityId = severityId;
        this.severityTypeDesc = severityTypeDesc;
    }
}
