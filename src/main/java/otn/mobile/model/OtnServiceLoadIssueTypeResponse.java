/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

/**
 *
 * @author EMantziou
 */
public class OtnServiceLoadIssueTypeResponse {

    private int IssueTypeId;
    private String name;

    private int appId;

    private int responseCode;

    private String message;
    
    public OtnServiceLoadIssueTypeResponse(){}

    public OtnServiceLoadIssueTypeResponse(int IssueTypeId, String name, int appId) {
        this.IssueTypeId = IssueTypeId;
        this.name = name;
        this.appId = appId;

    }

    public int getIssueTypeId() {
        return IssueTypeId;
    }

    public void setIssueTypeId(int IssueTypeId) {
        this.IssueTypeId = IssueTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
