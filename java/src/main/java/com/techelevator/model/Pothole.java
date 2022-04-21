package com.techelevator.model;

import java.util.Date;

public class Pothole {

    //INSTANCE VARIABLES

    private Integer potholeId;
    private Integer userId;
    private Integer progressStatusId;
    private Integer severityId;
    private String latitude;
    private String longitude;
    private String streetAddress; //Remove if not being used by API
    private Date reportedDate;
    private String inspectedDate;
    private String repairedDate;
    private String notes;

    //GETTERS

    public Integer getPotholeId() {
        return potholeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getProgressStatusId() {
        return progressStatusId;
    }

    public Integer getSeverityId() {
        return severityId;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Date getReportedDate() {
        return reportedDate;
    }

    public String getInspectedDate() {
        return inspectedDate;
    }

    public String getRepairedDate() {
        return repairedDate;
    }

    public String getNotes() {
        return notes;
    }


    //SETTERS

    public void setPotholeId(Integer potholeId) {
        this.potholeId = potholeId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setProgressStatusId(Integer progressStatusId) {
        this.progressStatusId = progressStatusId;
    }

    public void setSeverityId(Integer severityId) {
        this.severityId = severityId;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setReportedDate(Date reportedDate) {
        this.reportedDate = reportedDate;
    }

    public void setInspectedDate(String inspectedDate) {
        this.inspectedDate = inspectedDate;
    }

    public void setRepairedDate(String repairedDate) {
        this.repairedDate = repairedDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    //CONSTRUCTORS

    public Pothole() {};

    public Pothole(Integer potholeId, Integer userId,
                   Integer severityId, String latitude, String longitude, String streetAddress,
                   Date reportedDate, String inspectedDate, String repairedDate, String notes) {
        this.potholeId = potholeId;
        this.userId = userId;
        this.progressStatusId = 1;
        this.severityId = severityId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.streetAddress = streetAddress;
        this.reportedDate = reportedDate;
        this.inspectedDate = inspectedDate;
        this.repairedDate = repairedDate;
        this.notes = notes;
    }
}
