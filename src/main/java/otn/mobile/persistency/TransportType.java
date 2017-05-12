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
@Table(name = "transport_type", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransportType.findAll", query = "SELECT t FROM TransportType t"),
    @NamedQuery(name = "TransportType.findByTransportTypeId", query = "SELECT t FROM TransportType t WHERE t.transportTypeId = :transportTypeId"),
    @NamedQuery(name = "TransportType.findByName", query = "SELECT t FROM TransportType t WHERE t.name = :name")})
public class TransportType implements Serializable {
  
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "transport_type_id")
    private Integer transportTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;

    public TransportType() {
    }

    public TransportType(Integer transportTypeId) {
        this.transportTypeId = transportTypeId;
    }

    public TransportType(Integer transportTypeId, String name) {
        this.transportTypeId = transportTypeId;
        this.name = name;
    }

    public Integer getTransportTypeId() {
        return transportTypeId;
    }

    public void setTransportTypeId(Integer transportTypeId) {
        this.transportTypeId = transportTypeId;
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
        hash += (transportTypeId != null ? transportTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransportType)) {
            return false;
        }
        TransportType other = (TransportType) object;
        if ((this.transportTypeId == null && other.transportTypeId != null) || (this.transportTypeId != null && !this.transportTypeId.equals(other.transportTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.TransportType[ transportTypeId=" + transportTypeId + " ]";
    }   

}
