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
@Table(name = "weather_type", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WeatherType.findAll", query = "SELECT w FROM WeatherType w"),
    @NamedQuery(name = "WeatherType.findByWeatherId", query = "SELECT w FROM WeatherType w WHERE w.weatherId = :weatherId"),
    @NamedQuery(name = "WeatherType.findByCondition", query = "SELECT w FROM WeatherType w WHERE w.condition = :condition")})
public class WeatherType implements Serializable {
    @OneToMany(mappedBy = "weatherTypeId")
    private Collection<Weather> weatherCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "weather_id")
    private Integer weatherId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "condition")
    private String condition;
   

    public WeatherType() {
    }

    public WeatherType(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public WeatherType(Integer weatherId, String condition) {
        this.weatherId = weatherId;
        this.condition = condition;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weatherId != null ? weatherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeatherType)) {
            return false;
        }
        WeatherType other = (WeatherType) object;
        if ((this.weatherId == null && other.weatherId != null) || (this.weatherId != null && !this.weatherId.equals(other.weatherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.WeatherType[ weatherId=" + weatherId + " ]";
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Weather> getWeatherCollection() {
        return weatherCollection;
    }

    public void setWeatherCollection(Collection<Weather> weatherCollection) {
        this.weatherCollection = weatherCollection;
    }
    
}
