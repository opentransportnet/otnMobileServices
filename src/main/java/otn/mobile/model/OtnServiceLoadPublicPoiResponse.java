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
public class OtnServiceLoadPublicPoiResponse {

    private String message;

    private int responseCode;

    private int poiId;

    private OtnServicePoiLocation location;

    private List<OtnServicePoiAvgRatings> poiRatings;

    private String categoryName;

    private int verified;

    private int rate;

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

    public OtnServicePoiLocation getLocation() {
        return location;
    }

    public void setLocation(OtnServicePoiLocation location) {
        this.location = location;
    }

    public List<OtnServicePoiAvgRatings> getPoiRatings() {
        return poiRatings;
    }

    public void setPoiRatings(List<OtnServicePoiAvgRatings> poiRatings) {
        this.poiRatings = poiRatings;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}//end class
