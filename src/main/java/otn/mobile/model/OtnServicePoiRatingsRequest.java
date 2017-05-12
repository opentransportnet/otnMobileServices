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
public class OtnServicePoiRatingsRequest {

    private int poiId;
    private String userId;
    private int appId;
    private Boolean is_public;

    private List<OtnServicePoiRatings> poiRatings;

    @XmlElement
    public int getPoiId() {
        return poiId;
    }

    public void setPoiId(int poiId) {
        this.poiId = poiId;
    }

    @XmlElement
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @XmlElement
    public List<OtnServicePoiRatings> getPoiRatings() {
        return poiRatings;
    }

    public void setPoiRatings(List<OtnServicePoiRatings> poiRatings) {
        this.poiRatings = poiRatings;
    }

    @XmlElement
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    @XmlElement
    public Boolean isIs_public() {
        return is_public;
    }

    public void setIs_public(Boolean is_public) {
        this.is_public = is_public;
    }

}
