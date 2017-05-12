/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otn.mobile.persistency;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EMantziou
 */
@Entity
@Table(name = "trackfile_csv", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrackfileCsv.findAll", query = "SELECT t FROM TrackfileCsv t"),
    @NamedQuery(name = "TrackfileCsv.findByTrackfileCsvId", query = "SELECT t FROM TrackfileCsv t WHERE t.trackfileCsvId = :trackfileCsvId"),
    @NamedQuery(name = "TrackfileCsv.findByLongitude", query = "SELECT t FROM TrackfileCsv t WHERE t.longitude = :longitude"),
    @NamedQuery(name = "TrackfileCsv.findByLatitude", query = "SELECT t FROM TrackfileCsv t WHERE t.latitude = :latitude"),
    @NamedQuery(name = "TrackfileCsv.findByAltitude", query = "SELECT t FROM TrackfileCsv t WHERE t.altitude = :altitude"),
    @NamedQuery(name = "TrackfileCsv.findByRoadQuality", query = "SELECT t FROM TrackfileCsv t WHERE t.roadQuality = :roadQuality"),
    @NamedQuery(name = "TrackfileCsv.findByDistance", query = "SELECT t FROM TrackfileCsv t WHERE t.distance = :distance"),
    @NamedQuery(name = "TrackfileCsv.findBySpeed", query = "SELECT t FROM TrackfileCsv t WHERE t.speed = :speed"),
    @NamedQuery(name = "TrackfileCsv.findByTrackfileDate", query = "SELECT t FROM TrackfileCsv t WHERE t.trackfileDate = :trackfileDate")})
public class TrackfileCsv implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "trackfile_csv_id")
    private Integer trackfileCsvId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private float longitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private float latitude;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "altitude")
    private Float altitude;
    @Column(name = "road_quality")
    private Integer roadQuality;
    @Basic(optional = false)
    @NotNull
    @Column(name = "distance")
    private float distance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "speed")
    private float speed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "trackfile_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trackfileDate;
    @JoinColumn(name = "track_id", referencedColumnName = "track_id")
    @ManyToOne
    private Track trackId;

    public TrackfileCsv() {
    }

    public TrackfileCsv(Integer trackfileCsvId) {
        this.trackfileCsvId = trackfileCsvId;
    }

    public TrackfileCsv(Integer trackfileCsvId, float longitude, float latitude, float distance, float speed, Date trackfileDate) {
        this.trackfileCsvId = trackfileCsvId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.speed = speed;
        this.trackfileDate = trackfileDate;
    }

    public Integer getTrackfileCsvId() {
        return trackfileCsvId;
    }

    public void setTrackfileCsvId(Integer trackfileCsvId) {
        this.trackfileCsvId = trackfileCsvId;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public Float getAltitude() {
        return altitude;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    public Integer getRoadQuality() {
        return roadQuality;
    }

    public void setRoadQuality(Integer roadQuality) {
        this.roadQuality = roadQuality;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Date getTrackfileDate() {
        return trackfileDate;
    }

    public void setTrackfileDate(Date trackfileDate) {
        this.trackfileDate = trackfileDate;
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
        hash += (trackfileCsvId != null ? trackfileCsvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrackfileCsv)) {
            return false;
        }
        TrackfileCsv other = (TrackfileCsv) object;
        if ((this.trackfileCsvId == null && other.trackfileCsvId != null) || (this.trackfileCsvId != null && !this.trackfileCsvId.equals(other.trackfileCsvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.TrackfileCsv[ trackfileCsvId=" + trackfileCsvId + " ]";
    }
    
}
