/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import otn.mobile.persistency.PoiRating;

/**
 *
 * @author EMantziou
 */
public class OtnServiceAddPoiRequest {

    private int poiId;

    private int appId;

    private String userId;

    private String name;

//    private double latitude;
//    
//    private double longitude;
    private OtnServicePoiLocation location;

    private String address;

    private int poiSourceId;

    private int transportTypeId;

    private String description;

    private List<OtnServicePoiRatings> poiRatings;

//    private int locationRating;
//
//    private int frequencyRating;
//
//    private int NumberOfRoutesRating;
    private int verified;

    private int rating;

    @XmlElement
    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    @XmlElement
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    @XmlElement
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public OtnServicePoiLocation getLocation() {
        return location;
    }

    public void setLocation(OtnServicePoiLocation location) {
        this.location = location;
    }

    @XmlElement
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @XmlElement
    public int getPoiSourceId() {
        return poiSourceId;
    }

    public void setPoiSourceId(int poiSourceId) {
        this.poiSourceId = poiSourceId;
    }

    @XmlElement
    public int getTransportTypeId() {
        return transportTypeId;
    }

    public void setTransportTypeId(int transportTypeId) {
        this.transportTypeId = transportTypeId;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    @XmlElement
//    public int getLocationRating() {
//        return locationRating;
//    }
//
//    public void setLocationRating(int locationRating) {
//        this.locationRating = locationRating;
//    }
//
//    @XmlElement
//    public int getFrequencyRating() {
//        return frequencyRating;
//    }
//
//    public void setFrequencyRating(int frequencyRating) {
//        this.frequencyRating = frequencyRating;
//    }
//
//    @XmlElement
//    public int getNumberOfRoutesRating() {
//        return NumberOfRoutesRating;
//    }
//
//    public void setNumberOfRoutesRating(int NumberOfRoutesRating) {
//        this.NumberOfRoutesRating = NumberOfRoutesRating;
//    }
    @XmlElement
    public List<OtnServicePoiRatings> getPoiRatings() {
        return poiRatings;
    }

    public void setPoiRatings(List<OtnServicePoiRatings> poiRatings) {
        this.poiRatings = poiRatings;
    }

    @XmlElement
    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    @XmlElement
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
