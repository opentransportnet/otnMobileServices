/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

import java.util.List;

/**
 *
 * @author EMantziou
 */
public class OtnServiceLoadIssueTypeListResponse {

    private String message;

    private int responseCode;

    private List<OtnServiceLoadIssueTypeResponse> issueTypeList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<OtnServiceLoadIssueTypeResponse> getIssueTypeList() {
        return issueTypeList;
    }

    public void setIssueTypeList(List<OtnServiceLoadIssueTypeResponse> issueTypeList) {
        this.issueTypeList = issueTypeList;
    }

}//end class
