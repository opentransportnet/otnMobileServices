/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

import java.util.Date;

/**
 *
 * @author EMantziou
 */
public class OtnServiceLoadIssueListResponse {

    private int issueId;

    private String name;

    private String description;

    private byte[] picture;

    private Date report_date;

    private String issueTypeName;

    private double latitude;

    private double longitude;

    private String userId;

    private int appId;

    public OtnServiceLoadIssueListResponse(int issueId, String name, String description, byte[] picture, Date report_date,
            String issueTypeName, double latitude, double longitude, String userId, int appId) {
        this.issueId = issueId;
        this.name = name;
        this.description = description;
        this.picture = picture;
        this.report_date = report_date;
        this.issueTypeName = issueTypeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
        this.appId = appId;

    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getIssueTypeName() {
        return issueTypeName;
    }

    public void setIssueTypeName(String issueTypeName) {
        this.issueTypeName = issueTypeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

}
