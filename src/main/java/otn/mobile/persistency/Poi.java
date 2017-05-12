/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otn.mobile.persistency;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author EMantziou
 */
@Entity
@Table(name = "poi", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poi.findAll", query = "SELECT p FROM Poi p"),
    @NamedQuery(name = "Poi.findByPoiId", query = "SELECT p FROM Poi p WHERE p.poiId = :poiId"),
    @NamedQuery(name = "Poi.findByName", query = "SELECT p FROM Poi p WHERE p.name = :name"),
    @NamedQuery(name = "Poi.findByDescription", query = "SELECT p FROM Poi p WHERE p.description = :description"),
    @NamedQuery(name = "Poi.findByLatitude", query = "SELECT p FROM Poi p WHERE p.latitude = :latitude"),
    @NamedQuery(name = "Poi.findByLongitude", query = "SELECT p FROM Poi p WHERE p.longitude = :longitude"),
    @NamedQuery(name = "Poi.findByAddress", query = "SELECT p FROM Poi p WHERE p.address = :address"),
    @NamedQuery(name = "Poi.findByRating", query = "SELECT p FROM Poi p WHERE p.rating = :rating"),
    @NamedQuery(name = "Poi.findByVerified", query = "SELECT p FROM Poi p WHERE p.verified = :verified")})
public class Poi implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
   
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    @ManyToOne
    private Apps appId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "poi_id")
    private Integer poiId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "address")
    private String address;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "verified")
    private Integer verified;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "transport_type_id", referencedColumnName = "transport_type_id")
    @ManyToOne
    private TransportType transportTypeId;
    @JoinColumn(name = "poi_source_id", referencedColumnName = "source_id")
    @ManyToOne
    private Source poiSourceId;
   
    public Poi() {
    }

    public Poi(Integer poiId) {
        this.poiId = poiId;
    }

    public Poi(Integer poiId, String name, Double latitude, Double longitude, String address) {
        this.poiId = poiId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public Integer getPoiId() {
        return poiId;
    }

    public void setPoiId(Integer poiId) {
        this.poiId = poiId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
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

    public Source getPoiSourceId() {
        return poiSourceId;
    }

    public void setPoiSourceId(Source poiSourceId) {
        this.poiSourceId = poiSourceId;
    }

   
 
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (poiId != null ? poiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poi)) {
            return false;
        }
        Poi other = (Poi) object;
        if ((this.poiId == null && other.poiId != null) || (this.poiId != null && !this.poiId.equals(other.poiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.Poi[ poiId=" + poiId + " ]";
    }

    public Apps getAppId() {
        return appId;
    }

    public void setAppId(Apps appId) {
        this.appId = appId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

   
}
