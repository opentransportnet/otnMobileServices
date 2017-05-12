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
public class OtnServiceLoadTrackResponse {

    private String message;

    private int responseCode;

    private String name;

    private int trackId;
    
    private String userId;

    private String description;

    private byte[] picture;

    private double distance;

    private double duration;

    private double speed;

    private double elevation;

    private String transportName;

    private byte[] route_kml;

    private byte[] trackFilecsv;
    
    private String geomPoints;
    
    private String start_address;
    
    private String end_address;
    
    private boolean is_public;

    private List<OtnServicePoiAvgRatings> trackRatings;

    private List<OtnServiceWeatherRatings> weatherList;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public byte[] getTrackFilecsv() {
        return trackFilecsv;
    }

    public void setTrackFilecsv(byte[] trackFilecsv) {
        this.trackFilecsv = trackFilecsv;
    }

    public String getGeomPoints() {
        return geomPoints;
    }

    public void setGeomPoints(String geomPoints) {
        this.geomPoints = geomPoints;
    }

    public List<OtnServicePoiAvgRatings> getTrackRatings() {
        return trackRatings;
    }

    public void setTrackRatings(List<OtnServicePoiAvgRatings> trackRatings) {
        this.trackRatings = trackRatings;
    }

    public List<OtnServiceWeatherRatings> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<OtnServiceWeatherRatings> weatherList) {
        this.weatherList = weatherList;
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

    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }
    
    

}
