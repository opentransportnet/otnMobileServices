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
public class OtnServicesLoadPublicTracksListResponse {

    private String name;

    private int trackId;

    private String description;

    private byte[] picture;

    private double distance;

    private double duration;

    private double speed;

    private double elevation;

    private String transportName;

    private byte[] route_kml;

    private byte[] trackFileCsv;

    public OtnServicesLoadPublicTracksListResponse(String name, int trackId, String description, byte[] picture, double distance,
            double duration, double speed, double elevation, String transportName, byte[] route_kml, byte[] trackFileCsv) {
        
        
        this.name = name;
        this.trackId = trackId;
        this.description = description;
        this.picture = picture;
        this.distance = distance;
        this.duration = duration;
        this.speed = speed;
        this.elevation = elevation;
        this.transportName = transportName;
        this.route_kml = route_kml;
        this.trackFileCsv = trackFileCsv;

    }

    
    public OtnServicesLoadPublicTracksListResponse(String name, int trackId, String description, 
            String transportName) {
        
        
        this.name = name;
        this.trackId = trackId;
        this.description = description;
        this.distance = distance;
        this.duration = duration;
        this.speed = speed;
        this.elevation = elevation;
        this.transportName = transportName;
        this.route_kml = route_kml;
        this.trackFileCsv = trackFileCsv;

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

    
    public byte[] getRoute_kml() {
        return route_kml;
    }

    public void setRoute_kml(byte[] route_kml) {
        this.route_kml = route_kml;
    }

    public byte[] getTrackFileCsv() {
        return trackFileCsv;
    }

    public void setTrackFileCsv(byte[] trackFileCsv) {
        this.trackFileCsv = trackFileCsv;
    }

}
