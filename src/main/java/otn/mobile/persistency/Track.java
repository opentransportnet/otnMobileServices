/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otn.mobile.persistency;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author EMantziou
 */
@Entity
@Table(name = "track", schema = "otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Track.findAll", query = "SELECT t FROM Track t"),
    @NamedQuery(name = "Track.findByTrackId", query = "SELECT t FROM Track t WHERE t.trackId = :trackId"),
    @NamedQuery(name = "Track.findByName", query = "SELECT t FROM Track t WHERE t.name = :name"),
    @NamedQuery(name = "Track.findByDescription", query = "SELECT t FROM Track t WHERE t.description = :description"),
    @NamedQuery(name = "Track.findByDistance", query = "SELECT t FROM Track t WHERE t.distance = :distance"),
    @NamedQuery(name = "Track.findByDuration", query = "SELECT t FROM Track t WHERE t.duration = :duration"),
    @NamedQuery(name = "Track.findBySpeed", query = "SELECT t FROM Track t WHERE t.speed = :speed"),
    @NamedQuery(name = "Track.findByElevation", query = "SELECT t FROM Track t WHERE t.elevation = :elevation"),
    @NamedQuery(name = "Track.findByLatStart", query = "SELECT t FROM Track t WHERE t.latStart = :latStart"),
    @NamedQuery(name = "Track.findByLongStart", query = "SELECT t FROM Track t WHERE t.longStart = :longStart"),
    @NamedQuery(name = "Track.findByLatEnd", query = "SELECT t FROM Track t WHERE t.latEnd = :latEnd"),
    @NamedQuery(name = "Track.findByLongEnd", query = "SELECT t FROM Track t WHERE t.longEnd = :longEnd"),
    @NamedQuery(name = "Track.findByDatetimeStart", query = "SELECT t FROM Track t WHERE t.datetimeStart = :datetimeStart"),
    @NamedQuery(name = "Track.findByDatetimeEnd", query = "SELECT t FROM Track t WHERE t.datetimeEnd = :datetimeEnd"),
    @NamedQuery(name = "Track.findByIsPublic", query = "SELECT t FROM Track t WHERE t.isPublic = :isPublic"),
    @NamedQuery(name = "Track.findByStartAddress", query = "SELECT t FROM Track t WHERE t.startAddress = :startAddress"),
    @NamedQuery(name = "Track.findByEndAddress", query = "SELECT t FROM Track t WHERE t.endAddress = :endAddress"),
    @NamedQuery(name = "Track.findByCreationDate", query = "SELECT t FROM Track t WHERE t.creationDate = :creationDate")})
public class Track implements Serializable {

    @Lob
    @Column(name = "picture")
    private byte[] picture;
    @Basic(optional = false)
    @NotNull
    @Column(name = "distance")
    private double distance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    private double duration;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "speed")
    private Double speed;
    @Column(name = "elevation")
    private Double elevation;
    @Lob()
    @Column(name = "route_klm")
    private byte[] routeKlm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lat_start")
    private double latStart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "long_start")
    private double longStart;
    @Column(name = "lat_end")
    private Double latEnd;
    @Column(name = "long_end")
    private Double longEnd;
    @Lob
    @Column(name = "track_file_csv")
    private byte[] trackFileCsv;
    @Column(name = "verified")
    private Integer verified;

    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    @ManyToOne
    private Apps appId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "track_id")
    private Integer trackId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "description")
    private String description;
    @Column(name = "datetime_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeStart;
    @Column(name = "datetime_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeEnd;
    @Column(name = "is_public")
    private Boolean isPublic;
    @Size(max = 100)
    @Column(name = "start_address")
    private String startAddress;
    @Size(max = 100)
    @Column(name = "end_address")
    private String endAddress;
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "transport_type_id", referencedColumnName = "transport_type_id")
    @ManyToOne
    private TransportType transportTypeId;
    @JoinColumn(name = "parent_track_id", referencedColumnName = "track_id")
    @ManyToOne
    private Track parentTrackId;
    @JoinColumn(name = "route_source_id", referencedColumnName = "source_id")
    @ManyToOne
    private Source routeSourceId;

    public Track() {
    }

    public Track(Integer trackId) {
        this.trackId = trackId;
    }

    public Track(Integer trackId, String name, double distance, double duration, double latStart, double longStart) {
        this.trackId = trackId;
        this.name = name;
        this.distance = distance;
        this.duration = duration;
        this.latStart = latStart;
        this.longStart = longStart;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getRouteKlm() {
        return routeKlm;
    }

    public void setRouteKlm(byte[] routeKlm) {
        this.routeKlm = routeKlm;
    }

    public double getLatStart() {
        return latStart;
    }

    public void setLatStart(double latStart) {
        this.latStart = latStart;
    }

    public double getLongStart() {
        return longStart;
    }

    public void setLongStart(double longStart) {
        this.longStart = longStart;
    }

    public Double getLatEnd() {
        return latEnd;
    }

    public void setLatEnd(Double latEnd) {
        this.latEnd = latEnd;
    }

    public Double getLongEnd() {
        return longEnd;
    }

    public void setLongEnd(Double longEnd) {
        this.longEnd = longEnd;
    }

    public Date getDatetimeStart() {
        return datetimeStart;
    }

    public void setDatetimeStart(Date datetimeStart) {
        this.datetimeStart = datetimeStart;
    }

    public Date getDatetimeEnd() {
        return datetimeEnd;
    }

    public void setDatetimeEnd(Date datetimeEnd) {
        this.datetimeEnd = datetimeEnd;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public TransportType getTransportTypeId() {
        return transportTypeId;
    }

    public void setTransportTypeId(TransportType transportTypeId) {
        this.transportTypeId = transportTypeId;
    }

    public Track getParentTrackId() {
        return parentTrackId;
    }

    public void setParentTrackId(Track parentTrackId) {
        this.parentTrackId = parentTrackId;
    }

    public Source getRouteSourceId() {
        return routeSourceId;
    }

    public void setRouteSourceId(Source routeSourceId) {
        this.routeSourceId = routeSourceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trackId != null ? trackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Track)) {
            return false;
        }
        Track other = (Track) object;
        if ((this.trackId == null && other.trackId != null) || (this.trackId != null && !this.trackId.equals(other.trackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.Track[ trackId=" + trackId + " ]";
    }

    public Apps getAppId() {
        return appId;
    }

    public void setAppId(Apps appId) {
        this.appId = appId;
    }

    public byte[] getTrackFileCsv() {
        return trackFileCsv;
    }

    public void setTrackFileCsv(byte[] trackFileCsv) {
        this.trackFileCsv = trackFileCsv;
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

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }
}
