/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author EMantziou
 */
public class OtnServiceUpdateIssueTypeRequest {

    private int IssueTypeId;
    private String name;

    private int appId;

    @XmlElement
    public int getIssueTypeId() {
        return IssueTypeId;
    }

    public void setIssueTypeId(int IssueTypeId) {
        this.IssueTypeId = IssueTypeId;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

}
