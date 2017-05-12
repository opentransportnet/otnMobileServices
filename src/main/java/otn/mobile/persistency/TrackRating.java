/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.persistency;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EMantziou
 */
@Entity
@Table(name = "track_rating", schema = "otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrackRating.findAll", query = "SELECT t FROM TrackRating t"),
    @NamedQuery(name = "TrackRating.findByTrackRateId", query = "SELECT t FROM TrackRating t WHERE t.trackRateId = :trackRateId"),
    @NamedQuery(name = "TrackRating.findByRate", query = "SELECT t FROM TrackRating t WHERE t.rate = :rate")})
public class TrackRating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "track_rate_id")
    private Integer trackRateId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rate")
    private int rate;
    @JoinColumn(name = "track_rating_type_id", referencedColumnName = "track_rating_type_id")
    @ManyToOne
    private TrackRatingType trackRatingTypeId;
    @JoinColumn(name = "track_id", referencedColumnName = "track_id")
    @ManyToOne
    private Track trackId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public TrackRating() {
    }

    public TrackRating(Integer trackRateId) {
        this.trackRateId = trackRateId;
    }

    public TrackRating(Integer trackRateId, int rate) {
        this.trackRateId = trackRateId;
        this.rate = rate;
    }

    public Integer getTrackRateId() {
        return trackRateId;
    }

    public void setTrackRateId(Integer trackRateId) {
        this.trackRateId = trackRateId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public TrackRatingType getTrackRatingTypeId() {
        return trackRatingTypeId;
    }

    public void setTrackRatingTypeId(TrackRatingType trackRatingTypeId) {
        this.trackRatingTypeId = trackRatingTypeId;
    }

    public Track getTrackId() {
        return trackId;
    }

    public void setTrackId(Track trackId) {
        this.trackId = trackId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trackRateId != null ? trackRateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrackRating)) {
            return false;
        }
        TrackRating other = (TrackRating) object;
        if ((this.trackRateId == null && other.trackRateId != null) || (this.trackRateId != null && !this.trackRateId.equals(other.trackRateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.TrackRating[ trackRateId=" + trackRateId + " ]";
    }

}
