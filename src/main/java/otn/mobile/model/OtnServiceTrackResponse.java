/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EMantziou
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class OtnServiceTrackResponse implements Serializable {

    private String message;

    private int responseCode;

    private String name;

    private int trackId;

    private String description;

    private byte[] picture;

    private double distance;

    private double duration;

    private double speed;

    private double elevation;

    private byte[] route_kml;

    private double lat_start;

    private double lon_start;

    private double lat_end;

    private double lon_end;

    private Date datetime_start;

    private Date datetime_end;

    private boolean is_public;

    private String start_address;

    private String end_address;

    private byte[] trackFileCsv;

    private String transportName;

    private String userId;

    private String appName;

    private String sourceName;

//    private List<OtnServiceSubTracksResponse> trackList;
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

    public byte[] getRoute_kml() {
        return route_kml;
    }

    public void setRoute_kml(byte[] route_kml) {
        this.route_kml = route_kml;
    }

    public double getLat_start() {
        return lat_start;
    }

    public void setLat_start(double lat_start) {
        this.lat_start = lat_start;
    }

    public double getLon_start() {
        return lon_start;
    }

    public void setLon_start(double lon_start) {
        this.lon_start = lon_start;
    }

    public double getLat_end() {
        return lat_end;
    }

    public void setLat_end(double lat_end) {
        this.lat_end = lat_end;
    }

    public double getLon_end() {
        return lon_end;
    }

    public void setLon_end(double lon_end) {
        this.lon_end = lon_end;
    }

    public Date getDatetime_start() {
        return datetime_start;
    }

    public void setDatetime_start(Date datetime_start) {
        this.datetime_start = datetime_start;
    }

    public Date getDatetime_end() {
        return datetime_end;
    }

    public void setDatetime_end(Date datetime_end) {
        this.datetime_end = datetime_end;
    }

    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
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

    public byte[] getTrackFileCsv() {
        return trackFileCsv;
    }

    public void setTrackFileCsv(byte[] trackFileCsv) {
        this.trackFileCsv = trackFileCsv;
    }

    
    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

//    public List<OtnServiceSubTracksResponse> getTrackList() {
//        return trackList;
//    }
//
//    public void setTrackList(List<OtnServiceSubTracksResponse> trackList) {
//        this.trackList = trackList;
//    }
    public List<OtnServiceWeatherRatings> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<OtnServiceWeatherRatings> weatherList) {
        this.weatherList = weatherList;
    }

}
