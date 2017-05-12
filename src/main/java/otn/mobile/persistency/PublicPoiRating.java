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
@Table(name = "public_poi_rating", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PublicPoiRating.findAll", query = "SELECT p FROM PublicPoiRating p"),
    @NamedQuery(name = "PublicPoiRating.findByPublicPoiRatingId", query = "SELECT p FROM PublicPoiRating p WHERE p.publicPoiRatingId = :publicPoiRatingId"),
    @NamedQuery(name = "PublicPoiRating.findByRate", query = "SELECT p FROM PublicPoiRating p WHERE p.rate = :rate"),
    @NamedQuery(name = "PublicPoiRating.findByPublicPoiId", query = "SELECT p FROM PublicPoiRating p WHERE p.publicPoiId = :publicPoiId")})
public class PublicPoiRating implements Serializable {
    @JoinColumn(name = "public_poi_id", referencedColumnName = "public_poi_id")
    @ManyToOne
    private PublicPoi publicPoiId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "public_poi_rating_id")
    private Integer publicPoiRatingId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rate")
    private int rate;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @JoinColumn(name = "poi_rating_type_id", referencedColumnName = "poi_rating_type_id")
    @ManyToOne
    private PoiRatingType poiRatingTypeId;

    public PublicPoiRating() {
    }

    public PublicPoiRating(Integer publicPoiRatingId) {
        this.publicPoiRatingId = publicPoiRatingId;
    }

    public PublicPoiRating(Integer publicPoiRatingId, int rate) {
        this.publicPoiRatingId = publicPoiRatingId;
        this.rate = rate;
    }

    public Integer getPublicPoiRatingId() {
        return publicPoiRatingId;
    }

    public void setPublicPoiRatingId(Integer publicPoiRatingId) {
        this.publicPoiRatingId = publicPoiRatingId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }


    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public PoiRatingType getPoiRatingTypeId() {
        return poiRatingTypeId;
    }

    public void setPoiRatingTypeId(PoiRatingType poiRatingTypeId) {
        this.poiRatingTypeId = poiRatingTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (publicPoiRatingId != null ? publicPoiRatingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PublicPoiRating)) {
            return false;
        }
        PublicPoiRating other = (PublicPoiRating) object;
        if ((this.publicPoiRatingId == null && other.publicPoiRatingId != null) || (this.publicPoiRatingId != null && !this.publicPoiRatingId.equals(other.publicPoiRatingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.PublicPoiRating[ publicPoiRatingId=" + publicPoiRatingId + " ]";
    }

    public PublicPoi getPublicPoiId() {
        return publicPoiId;
    }

    public void setPublicPoiId(PublicPoi publicPoiId) {
        this.publicPoiId = publicPoiId;
    }
    
}
