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
@Table(name = "web_analytics", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WebAnalytics.findAll", query = "SELECT w FROM WebAnalytics w"),
    @NamedQuery(name = "WebAnalytics.findByAnalyticsId", query = "SELECT w FROM WebAnalytics w WHERE w.analyticsId = :analyticsId"),
    @NamedQuery(name = "WebAnalytics.findByViewedCount", query = "SELECT w FROM WebAnalytics w WHERE w.viewedCount = :viewedCount"),
    @NamedQuery(name = "WebAnalytics.findByNavigatedCount", query = "SELECT w FROM WebAnalytics w WHERE w.navigatedCount = :navigatedCount")})
public class WebAnalytics implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "analytics_id")
    private Integer analyticsId;
    @Column(name = "viewed_count")
    private Integer viewedCount;
    @Column(name = "navigated_count")
    private Integer navigatedCount;
    @JoinColumn(name = "track_id", referencedColumnName = "track_id")
    @ManyToOne
    private Track trackId;

    public WebAnalytics() {
    }

    public WebAnalytics(Integer analyticsId) {
        this.analyticsId = analyticsId;
    }

    public Integer getAnalyticsId() {
        return analyticsId;
    }

    public void setAnalyticsId(Integer analyticsId) {
        this.analyticsId = analyticsId;
    }

    public Integer getViewedCount() {
        return viewedCount;
    }

    public void setViewedCount(Integer viewedCount) {
        this.viewedCount = viewedCount;
    }

    public Integer getNavigatedCount() {
        return navigatedCount;
    }

    public void setNavigatedCount(Integer navigatedCount) {
        this.navigatedCount = navigatedCount;
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
        hash += (analyticsId != null ? analyticsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebAnalytics)) {
            return false;
        }
        WebAnalytics other = (WebAnalytics) object;
        if ((this.analyticsId == null && other.analyticsId != null) || (this.analyticsId != null && !this.analyticsId.equals(other.analyticsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.WebAnalytics[ analyticsId=" + analyticsId + " ]";
    }
    
}
