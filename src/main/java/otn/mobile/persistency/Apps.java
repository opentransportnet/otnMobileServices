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
@Table(name = "apps", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apps.findAll", query = "SELECT a FROM Apps a"),
    @NamedQuery(name = "Apps.findByAppId", query = "SELECT a FROM Apps a WHERE a.appId = :appId"),
    @NamedQuery(name = "Apps.findByName", query = "SELECT a FROM Apps a WHERE a.name = :name")})
public class Apps implements Serializable {
  
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "app_id")
    private Integer appId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;

    public Apps() {
    }

    public Apps(Integer appId) {
        this.appId = appId;
    }

    public Apps(Integer appId, String name) {
        this.appId = appId;
        this.name = name;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
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
        hash += (appId != null ? appId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Apps)) {
            return false;
        }
        Apps other = (Apps) object;
        if ((this.appId == null && other.appId != null) || (this.appId != null && !this.appId.equals(other.appId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.Apps[ appId=" + appId + " ]";
    }

   
}
