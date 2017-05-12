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
@Table(name = "public_poi_verified", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PublicPoiVerified.findAll", query = "SELECT p FROM PublicPoiVerified p"),
    @NamedQuery(name = "PublicPoiVerified.findByPublicPoiVerifiedId", query = "SELECT p FROM PublicPoiVerified p WHERE p.publicPoiVerifiedId = :publicPoiVerifiedId")})
public class PublicPoiVerified implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "public_poi_verified_id")
    private Integer publicPoiVerifiedId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "public_poi_id", referencedColumnName = "public_poi_id")
    @ManyToOne
    private PublicPoi publicPoiId;

    public PublicPoiVerified() {
    }

    public PublicPoiVerified(Integer publicPoiVerifiedId) {
        this.publicPoiVerifiedId = publicPoiVerifiedId;
    }

    public Integer getPublicPoiVerifiedId() {
        return publicPoiVerifiedId;
    }

    public void setPublicPoiVerifiedId(Integer publicPoiVerifiedId) {
        this.publicPoiVerifiedId = publicPoiVerifiedId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public PublicPoi getPublicPoiId() {
        return publicPoiId;
    }

    public void setPublicPoiId(PublicPoi publicPoiId) {
        this.publicPoiId = publicPoiId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (publicPoiVerifiedId != null ? publicPoiVerifiedId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PublicPoiVerified)) {
            return false;
        }
        PublicPoiVerified other = (PublicPoiVerified) object;
        if ((this.publicPoiVerifiedId == null && other.publicPoiVerifiedId != null) || (this.publicPoiVerifiedId != null && !this.publicPoiVerifiedId.equals(other.publicPoiVerifiedId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.PublicPoiVerified[ publicPoiVerifiedId=" + publicPoiVerifiedId + " ]";
    }
    
}
