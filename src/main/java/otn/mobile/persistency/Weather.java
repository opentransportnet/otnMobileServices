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
@Table(name = "weather", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weather.findAll", query = "SELECT w FROM Weather w"),
    @NamedQuery(name = "Weather.findByWeatherId", query = "SELECT w FROM Weather w WHERE w.weatherId = :weatherId")})
public class Weather implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "weather_id")
    private Integer weatherId;
    @JoinColumn(name = "weather_type_id", referencedColumnName = "weather_id")
    @ManyToOne
    private WeatherType weatherTypeId;
    @JoinColumn(name = "track_id", referencedColumnName = "track_id")
    @ManyToOne
    private Track trackId;

    public Weather() {
    }

    public Weather(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public WeatherType getWeatherTypeId() {
        return weatherTypeId;
    }

    public void setWeatherTypeId(WeatherType weatherTypeId) {
        this.weatherTypeId = weatherTypeId;
    }

    public Track getTrackId() {
        return trackId;
    }

    public void setTrackId(Track trackId) {
        this.trackId = trackId;
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
        if (!(object instanceof Weather)) {
            return false;
        }
        Weather other = (Weather) object;
        if ((this.weatherId == null && other.weatherId != null) || (this.weatherId != null && !this.weatherId.equals(other.weatherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.Weather[ weatherId=" + weatherId + " ]";
    }
    
}
