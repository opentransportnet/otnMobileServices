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
public class OtnServiceDeleteUserContentRequest {

    private String userId;

    private int appId;

    @XmlElement
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @XmlElement
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

}//end class
