/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author EMantziou
 */
public class OtnServiceTrackRequest {

    private int trackId;

    private String name;

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

//    private Date creation_date;
    private boolean is_public;

    private String start_address;

    private String end_address;

    private int transportId;

    private String userId;

    private int appId;

//    private int weatherId;
    private int sourceId;

    private byte[] trackFileCsv;

    private List<OtnServiceSubTracksRequest> subTracksList;

    private List<OtnServicePoiRatings> trackRatings;

    private List<OtnServiceWeatherRatings> weatherList;

    private List<OtnServiceGeometryPoints> geometryPoints;

    @XmlElement
    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @XmlElement
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @XmlElement
    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @XmlElement
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @XmlElement
    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    @XmlElement
    public byte[] getRoute_kml() {
        return route_kml;
    }

    public void setRoute_kml(byte[] route_kml) {
        this.route_kml = route_kml;
    }

    @XmlElement
    public double getLat_start() {
        return lat_start;
    }

    public void setLat_start(double lat_start) {
        this.lat_start = lat_start;
    }

    @XmlElement
    public double getLon_start() {
        return lon_start;
    }

    public void setLon_start(double lon_start) {
        this.lon_start = lon_start;
    }

    @XmlElement
    public double getLat_end() {
        return lat_end;
    }

    public void setLat_end(double lat_end) {
        this.lat_end = lat_end;
    }

    @XmlElement
    public double getLon_end() {
        return lon_end;
    }

    public void setLon_end(double lon_end) {
        this.lon_end = lon_end;
    }

    @XmlElement
    public Date getDatetime_start() {
        return datetime_start;
    }

    public void setDatetime_start(Date datetime_start) {
        this.datetime_start = datetime_start;
    }

    @XmlElement
    public Date getDatetime_end() {
        return datetime_end;
    }

    public void setDatetime_end(Date datetime_end) {
        this.datetime_end = datetime_end;
    }

//    @XmlElement
//    public Date getCreation_date() {
//        return creation_date;
//    }
//
//    public void setCreation_date(Date creation_date) {
//        this.creation_date = creation_date;
//    }
    @XmlElement
    public boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    @XmlElement
    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    @XmlElement
    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    @XmlElement
    public int getTransportId() {
        return transportId;
    }

    public void setTransportId(int transportId) {
        this.transportId = transportId;
    }

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

//    @XmlElement
//    public int getWeatherId() {
//        return weatherId;
//    }
//
//    public void setWeatherId(int weatherId) {
//        this.weatherId = weatherId;
//    }
    @XmlElement
    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    @XmlElement
    public List<OtnServiceSubTracksRequest> getSubTracksList() {
        return subTracksList;
    }

    public void setSubTracksList(List<OtnServiceSubTracksRequest> subTracksList) {
        this.subTracksList = subTracksList;
    }

    @XmlElement
    public byte[] getTrackFileCsv() {
        return trackFileCsv;
    }

    public void setTrackFileCsv(byte[] trackFileCsv) {
        this.trackFileCsv = trackFileCsv;
    }

    @XmlElement
    public List<OtnServicePoiRatings> getTrackRatings() {
        return trackRatings;
    }

    public void setTrackRatings(List<OtnServicePoiRatings> trackRatings) {
        this.trackRatings = trackRatings;
    }

    @XmlElement
    public List<OtnServiceWeatherRatings> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<OtnServiceWeatherRatings> weatherList) {
        this.weatherList = weatherList;
    }

    @XmlElement
    public List<OtnServiceGeometryPoints> getGeometryPoints() {
        return geometryPoints;
    }

    public void setGeometryPoints(List<OtnServiceGeometryPoints> geometryPoints) {
        this.geometryPoints = geometryPoints;
    }
}
