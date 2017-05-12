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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EMantziou
 */
@Entity
@Table(name = "poi_rating", schema = "otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PoiRating.findAll", query = "SELECT p FROM PoiRating p"),
    @NamedQuery(name = "PoiRating.findByPoiRatingId", query = "SELECT p FROM PoiRating p WHERE p.poiRatingId = :poiRatingId"),
    @NamedQuery(name = "PoiRating.findByRate", query = "SELECT p FROM PoiRating p WHERE p.rate = :rate")})
public class PoiRating implements Serializable {

    @JoinColumn(name = "poi_rating_type_id", referencedColumnName = "poi_rating_type_id")
    @ManyToOne
    private PoiRatingType poiRatingTypeId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "poi_rating_id")
    private Integer poiRatingId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "rate")
    private int rate;
    @JoinColumn(name = "poi_id", referencedColumnName = "poi_id")
    @ManyToOne
    private Poi poiId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public PoiRating() {
    }

    public PoiRating(Integer poiRatingId) {
        this.poiRatingId = poiRatingId;
    }

    public PoiRating(Integer poiRatingId, int rate) {
        this.poiRatingId = poiRatingId;

        this.rate = rate;
    }

    public Integer getPoiRatingId() {
        return poiRatingId;
    }

    public void setPoiRatingId(Integer poiRatingId) {
        this.poiRatingId = poiRatingId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Poi getPoiId() {
        return poiId;
    }

    public void setPoiId(Poi poiId) {
        this.poiId = poiId;
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
        hash += (poiRatingId != null ? poiRatingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoiRating)) {
            return false;
        }
        PoiRating other = (PoiRating) object;
        if ((this.poiRatingId == null && other.poiRatingId != null) || (this.poiRatingId != null && !this.poiRatingId.equals(other.poiRatingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.PoiRating[ poiRatingId=" + poiRatingId + " ]";
    }

    public PoiRatingType getPoiRatingTypeId() {
        return poiRatingTypeId;
    }

    public void setPoiRatingTypeId(PoiRatingType poiRatingTypeId) {
        this.poiRatingTypeId = poiRatingTypeId;
    }

}
