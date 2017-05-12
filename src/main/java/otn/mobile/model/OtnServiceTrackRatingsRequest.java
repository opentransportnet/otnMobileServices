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
public class OtnServiceTrackRatingsRequest {

    private int trackId;
    private String userId;
    private int appId;

    private List<OtnServicePoiRatings> trackRatings;

    @XmlElement
    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
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

    @XmlElement
    public List<OtnServicePoiRatings> getTrackRatings() {
        return trackRatings;
    }

    public void setTrackRatings(List<OtnServicePoiRatings> trackRatings) {
        this.trackRatings = trackRatings;
    }

}//end class
