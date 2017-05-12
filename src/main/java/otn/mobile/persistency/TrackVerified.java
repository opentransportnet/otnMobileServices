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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EMantziou
 */
@Entity
@Table(name = "track_verified", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrackVerified.findAll", query = "SELECT t FROM TrackVerified t"),
    @NamedQuery(name = "TrackVerified.findByTrackVerifiedId", query = "SELECT t FROM TrackVerified t WHERE t.trackVerifiedId = :trackVerifiedId")})
public class TrackVerified implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "track_verified_id")
    private Integer trackVerifiedId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "track_id", referencedColumnName = "track_id")
    @ManyToOne
    private Track trackId;

    public TrackVerified() {
    }

    public TrackVerified(Integer trackVerifiedId) {
        this.trackVerifiedId = trackVerifiedId;
    }

    public Integer getTrackVerifiedId() {
        return trackVerifiedId;
    }

    public void setTrackVerifiedId(Integer trackVerifiedId) {
        this.trackVerifiedId = trackVerifiedId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Track getTrackId() {
        return trackId;
    }

    public void setTrackId(Track trackId) {
        this.trackId = trackId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trackVerifiedId != null ? trackVerifiedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrackVerified)) {
            return false;
        }
        TrackVerified other = (TrackVerified) object;
        if ((this.trackVerifiedId == null && other.trackVerifiedId != null) || (this.trackVerifiedId != null && !this.trackVerifiedId.equals(other.trackVerifiedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.TrackVerified[ trackVerifiedId=" + trackVerifiedId + " ]";
    }
    
}
