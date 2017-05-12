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
public class OtnServiceLoadPublicTracksResponse {
    private String message;

    private int responseCode;
    
    private List<OtnServicesLoadPublicTracksListResponse> trackList;

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

    public List<OtnServicesLoadPublicTracksListResponse> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<OtnServicesLoadPublicTracksListResponse> trackList) {
        this.trackList = trackList;
    }
    
    
    
}
