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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "track_rating_type", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrackRatingType.findAll", query = "SELECT t FROM TrackRatingType t"),
    @NamedQuery(name = "TrackRatingType.findByTrackRatingTypeId", query = "SELECT t FROM TrackRatingType t WHERE t.trackRatingTypeId = :trackRatingTypeId"),
    @NamedQuery(name = "TrackRatingType.findByName", query = "SELECT t FROM TrackRatingType t WHERE t.name = :name")})
public class TrackRatingType implements Serializable {
   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "track_rating_type_id")
    private Integer trackRatingTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;

    public TrackRatingType() {
    }

    public TrackRatingType(Integer trackRatingTypeId) {
        this.trackRatingTypeId = trackRatingTypeId;
    }

    public TrackRatingType(Integer trackRatingTypeId, String name) {
        this.trackRatingTypeId = trackRatingTypeId;
        this.name = name;
    }

    public Integer getTrackRatingTypeId() {
        return trackRatingTypeId;
    }

    public void setTrackRatingTypeId(Integer trackRatingTypeId) {
        this.trackRatingTypeId = trackRatingTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trackRatingTypeId != null ? trackRatingTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrackRatingType)) {
            return false;
        }
        TrackRatingType other = (TrackRatingType) object;
        if ((this.trackRatingTypeId == null && other.trackRatingTypeId != null) || (this.trackRatingTypeId != null && !this.trackRatingTypeId.equals(other.trackRatingTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.TrackRatingType[ trackRatingTypeId=" + trackRatingTypeId + " ]";
    }

  
}
