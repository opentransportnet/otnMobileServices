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
public class OtnServiceDeleteIssueTypeRequest {
    private int IssueTypeId;

    @XmlElement
    public int getIssueTypeId() {
        return IssueTypeId;
    }

    public void setIssueTypeId(int IssueTypeId) {
        this.IssueTypeId = IssueTypeId;
    }
    
}//end class
