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
public class OtnServicesMyTracksListResponse {

    private String name;

    private int trackId;

    private String transportName;

    private byte[] picture;

    private double distance;

    private double duration;
    
    private String start_address;
    
    private String end_address;
    
//    private List<OtnServiceSubTracksResponse> subTrackList;
    
    public OtnServicesMyTracksListResponse(){
    }
    

    
        public OtnServicesMyTracksListResponse(String name, int id, String transportName, byte[] picture, 
                double distance, double duration, String start_address, String end_address ){
        this.name = name;
        this.trackId = id;
        this.transportName = transportName;
        this.picture = picture;
        this.distance = distance;
        this.duration = duration;
        this.start_address = start_address;
        this.end_address = end_address;
       
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
    
    

//    public List<OtnServiceSubTracksResponse> getSubTrackList() {
//        return subTrackList;
//    }
//
//    public void setSubTrackList(List<OtnServiceSubTracksResponse> subTrackList) {
//        this.subTrackList = subTrackList;
//    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }
    
    

    
}
