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
@Table(name = "poi_rating_type", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PoiRatingType.findAll", query = "SELECT p FROM PoiRatingType p"),
    @NamedQuery(name = "PoiRatingType.findByPoiRatingTypeId", query = "SELECT p FROM PoiRatingType p WHERE p.poiRatingTypeId = :poiRatingTypeId"),
    @NamedQuery(name = "PoiRatingType.findByName", query = "SELECT p FROM PoiRatingType p WHERE p.name = :name")})
public class PoiRatingType implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "poi_rating_type_id")
    private Integer poiRatingTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
   
    public PoiRatingType() {
    }

    public PoiRatingType(Integer poiRatingTypeId) {
        this.poiRatingTypeId = poiRatingTypeId;
    }

    public PoiRatingType(Integer poiRatingTypeId, String name) {
        this.poiRatingTypeId = poiRatingTypeId;
        this.name = name;
    }

    public Integer getPoiRatingTypeId() {
        return poiRatingTypeId;
    }

    public void setPoiRatingTypeId(Integer poiRatingTypeId) {
        this.poiRatingTypeId = poiRatingTypeId;
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
        hash += (poiRatingTypeId != null ? poiRatingTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PoiRatingType)) {
            return false;
        }
        PoiRatingType other = (PoiRatingType) object;
        if ((this.poiRatingTypeId == null && other.poiRatingTypeId != null) || (this.poiRatingTypeId != null && !this.poiRatingTypeId.equals(other.poiRatingTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.PoiRatingType[ poiRatingTypeId=" + poiRatingTypeId + " ]";
    }

}
