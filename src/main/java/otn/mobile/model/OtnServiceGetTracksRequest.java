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
public class OtnServiceGetTracksRequest {
    
    private String userId;

    private int appId;
    
    private Boolean isPublic;
    
    private double fromLat;
    
    private double fromLon;
    
    private double toLat;
    
    private double toLon;
    
    private double radius;
    
    private Boolean isMine;

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

    @XmlElement
    public Boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    @XmlElement
    public double getFromLat() {
        return fromLat;
    }

    public void setFromLat(double fromLat) {
        this.fromLat = fromLat;
    }

    @XmlElement
    public double getFromLon() {
        return fromLon;
    }

    public void setFromLon(double fromLon) {
        this.fromLon = fromLon;
    }

    @XmlElement
    public double getToLat() {
        return toLat;
    }

    public void setToLat(double toLat) {
        this.toLat = toLat;
    }

    @XmlElement
    public double getToLon() {
        return toLon;
    }

    public void setToLon(double toLon) {
        this.toLon = toLon;
    }

    @XmlElement
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @XmlElement
    public Boolean isIsMine() {
        return isMine;
    }

    public void setIsMine(Boolean isMine) {
        this.isMine = isMine;
    }
    

}// end class
