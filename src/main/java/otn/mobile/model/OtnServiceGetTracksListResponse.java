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
public class OtnServiceGetTracksListResponse {

    private String name;

    private int trackId;

    private String description;

    private String userId;

    private byte[] picture;

    private double distance;

    private double duration;

    private double speed;

    private double elevation;

    private String transportName;

    private String start_address;

    private String end_address;
    
   private boolean is_public;

    private String geomPoints;

    public OtnServiceGetTracksListResponse(String name, int trackId, String userId, String description, byte[] picture, double distance,
            double duration, double speed, double elevation, String transportName,
            String start_address, String end_address, boolean is_public, String geomPoints) {

        this.name = name;
        this.trackId = trackId;
        this.description = description;
        this.userId = userId;
        this.picture = picture;
        this.distance = distance;
        this.duration = duration;
        this.speed = speed;
        this.elevation = elevation;
        this.transportName = transportName;
        this.start_address = start_address;
        this.end_address = end_address;
        this.is_public = is_public;
        this.geomPoints = geomPoints;

    }

    public OtnServiceGetTracksListResponse(String name, int trackId, String description,
            String transportName) {

        this.name = name;
        this.trackId = trackId;
        this.description = description;

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

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

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

    public String getGeomPoints() {
        return geomPoints;
    }

    public void setGeomPoints(String geomPoints) {
        this.geomPoints = geomPoints;
    }

    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }
    
    

}// end class
