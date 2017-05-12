/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author EMantziou
 */
public class OtnServiceAddPublicPoiRequest {

    private OtnServicePoiLocation location;
    private List<OtnServicePoiRatings> poiRatings;

    private int categoryId;
    private int verified;

    private int rating;

    @XmlElement
    public OtnServicePoiLocation getLocation() {
        return location;
    }

    public void setLocation(OtnServicePoiLocation location) {
        this.location = location;
    }

    @XmlElement
    public List<OtnServicePoiRatings> getPoiRatings() {
        return poiRatings;
    }

    public void setPoiRatings(List<OtnServicePoiRatings> poiRatings) {
        this.poiRatings = poiRatings;
    }

    @XmlElement
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

}//end class
