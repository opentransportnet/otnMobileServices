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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author EMantziou
 */
@Entity
@Table(name = "public_poi", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PublicPoi.findAll", query = "SELECT p FROM PublicPoi p"),
    @NamedQuery(name = "PublicPoi.findByPublicPoiId", query = "SELECT p FROM PublicPoi p WHERE p.publicPoiId = :publicPoiId"),
    @NamedQuery(name = "PublicPoi.findByLatitude", query = "SELECT p FROM PublicPoi p WHERE p.latitude = :latitude"),
    @NamedQuery(name = "PublicPoi.findByLongitude", query = "SELECT p FROM PublicPoi p WHERE p.longitude = :longitude"),
    @NamedQuery(name = "PublicPoi.findByVerify", query = "SELECT p FROM PublicPoi p WHERE p.verify = :verify"),
    @NamedQuery(name = "PublicPoi.findByRate", query = "SELECT p FROM PublicPoi p WHERE p.rate = :rate")})
public class PublicPoi implements Serializable {
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "public_poi_id")
    private Integer publicPoiId;
    @Column(name = "verify")
    private Integer verify;
    @Column(name = "rate")
    private Integer rate;
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @ManyToOne
    private PublicPoiCategory categoryId;

    public PublicPoi() {
    }

    public PublicPoi(Integer publicPoiId) {
        this.publicPoiId = publicPoiId;
    }

    public Integer getPublicPoiId() {
        return publicPoiId;
    }

    public void setPublicPoiId(Integer publicPoiId) {
        this.publicPoiId = publicPoiId;
    }

    public Integer getVerify() {
        return verify;
    }

    public void setVerify(Integer verify) {
        this.verify = verify;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public PublicPoiCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(PublicPoiCategory categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (publicPoiId != null ? publicPoiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PublicPoi)) {
            return false;
        }
        PublicPoi other = (PublicPoi) object;
        if ((this.publicPoiId == null && other.publicPoiId != null) || (this.publicPoiId != null && !this.publicPoiId.equals(other.publicPoiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.PublicPoi[ publicPoiId=" + publicPoiId + " ]";
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
