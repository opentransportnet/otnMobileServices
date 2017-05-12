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
public class OtnServiceStatisticsResponse {
     private int responseCode;
    
    private String message;
    
    private int views;
    
    private int navigation;
    
    private int trackId;

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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getNavigation() {
        return navigation;
    }

    public void setNavigation(int navigation) {
        this.navigation = navigation;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }
    
    
    
    
}//end class
