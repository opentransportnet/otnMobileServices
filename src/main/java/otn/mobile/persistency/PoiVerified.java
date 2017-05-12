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
@Table(name = "poi_verified", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PoiVerified.findAll", query = "SELECT p FROM PoiVerified p"),
    @NamedQuery(name = "PoiVerified.findByPoiVerifiedId", query = "SELECT p FROM PoiVerified p WHERE p.poiVerifiedId = :poiVerifiedId")})
public class PoiVerified implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "poi_verified_id")
    private Integer poiVerifiedId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "poi_id", referencedColumnName = "poi_id")
    @ManyToOne
    private Poi poiId;

    public PoiVerified() {
    }

    public PoiVerified(Integer poiVerifiedId) {
        this.poiVerifiedId = poiVerifiedId;
    }

    public Integer getPoiVerifiedId() {
        return poiVerifiedId;
    }

    public void setPoiVerifiedId(Integer poiVerifiedId) {
        this.poiVerifiedId = poiVerifiedId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Poi getPoiId() {
        return poiId;
    }

    public void setPoiId(Poi poiId) {
        this.poiId = poiId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (poiVerifiedId != null ? poiVerifiedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoiVerified)) {
            return false;
        }
        PoiVerified other = (PoiVerified) object;
        if ((this.poiVerifiedId == null && other.poiVerifiedId != null) || (this.poiVerifiedId != null && !this.poiVerifiedId.equals(other.poiVerifiedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.PoiVerified[ poiVerifiedId=" + poiVerifiedId + " ]";
    }
    
}
