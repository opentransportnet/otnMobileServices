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
public class OtnServiceLoadPoiResponse {

    private String message;

    private int responseCode;
    
    private int poiId;

    private int appId;

    private String name;

    private OtnServicePoiLocation location;

    private String address;

    private String poiSourceName;

    private String transportName;

    private String description;

    private List<OtnServicePoiAvgRatings> poiRatings;

    private int verified;
    
    private boolean user_verified;

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

    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OtnServicePoiLocation getLocation() {
        return location;
    }

    public void setLocation(OtnServicePoiLocation location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPoiSourceName() {
        return poiSourceName;
    }

    public void setPoiSourceName(String poiSourceName) {
        this.poiSourceName = poiSourceName;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OtnServicePoiAvgRatings> getPoiRatings() {
        return poiRatings;
    }

    public void setPoiRatings(List<OtnServicePoiAvgRatings> poiRatings) {
        this.poiRatings = poiRatings;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public boolean isUser_verified() {
        return user_verified;
    }

    public void setUser_verified(boolean user_verified) {
        this.user_verified = user_verified;
    }
    
    
    

}// end class
