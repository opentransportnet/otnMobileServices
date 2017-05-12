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
@Table(name = "public_poi_category", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PublicPoiCategory.findAll", query = "SELECT p FROM PublicPoiCategory p"),
    @NamedQuery(name = "PublicPoiCategory.findByCategoryId", query = "SELECT p FROM PublicPoiCategory p WHERE p.categoryId = :categoryId"),
    @NamedQuery(name = "PublicPoiCategory.findByName", query = "SELECT p FROM PublicPoiCategory p WHERE p.name = :name")})
public class PublicPoiCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "category_id")
    private Integer categoryId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "name")
    private String name;
   

    public PublicPoiCategory() {
    }

    public PublicPoiCategory(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public PublicPoiCategory(Integer categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PublicPoiCategory)) {
            return false;
        }
        PublicPoiCategory other = (PublicPoiCategory) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.PublicPoiCategory[ categoryId=" + categoryId + " ]";
    }

}
