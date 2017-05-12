/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package otn.mobile.persistency;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author EMantziou
 */
@Entity
@Table(name = "issue", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Issue.findAll", query = "SELECT i FROM Issue i"),
    @NamedQuery(name = "Issue.findByIssueId", query = "SELECT i FROM Issue i WHERE i.issueId = :issueId"),
    @NamedQuery(name = "Issue.findByName", query = "SELECT i FROM Issue i WHERE i.name = :name"),
    @NamedQuery(name = "Issue.findByDescription", query = "SELECT i FROM Issue i WHERE i.description = :description"),
    @NamedQuery(name = "Issue.findByReportDate", query = "SELECT i FROM Issue i WHERE i.reportDate = :reportDate"),
    @NamedQuery(name = "Issue.findByLatitude", query = "SELECT i FROM Issue i WHERE i.latitude = :latitude"),
    @NamedQuery(name = "Issue.findByLongitude", query = "SELECT i FROM Issue i WHERE i.longitude = :longitude")})
public class Issue implements Serializable {
    @Lob
    @Column(name = "picture")
    private byte[] picture;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitude")
    private double latitude;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitude")
    private double longitude;
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    @ManyToOne
    private Apps appId;
    @JoinColumn(name = "track_id", referencedColumnName = "track_id")
    @ManyToOne
    private Track trackId;
    @JoinColumn(name = "issue_type_int", referencedColumnName = "issue_type_id")
    @ManyToOne
    private IssueType issueTypeInt;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "issue_id")
    private Integer issueId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "report_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;

    public Issue() {
    }

    public Issue(Integer issueId) {
        this.issueId = issueId;
    }

    public Issue(Integer issueId, String name, Date reportDate, double latitude, double longitude) {
        this.issueId = issueId;
        this.name = name;
        this.reportDate = reportDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (issueId != null ? issueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Issue)) {
            return false;
        }
        Issue other = (Issue) object;
        if ((this.issueId == null && other.issueId != null) || (this.issueId != null && !this.issueId.equals(other.issueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.Issue[ issueId=" + issueId + " ]";
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Track getTrackId() {
        return trackId;
    }

    public void setTrackId(Track trackId) {
        this.trackId = trackId;
    }

    public IssueType getIssueTypeInt() {
        return issueTypeInt;
    }

    public void setIssueTypeInt(IssueType issueTypeInt) {
        this.issueTypeInt = issueTypeInt;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Apps getAppId() {
        return appId;
    }

    public void setAppId(Apps appId) {
        this.appId = appId;
    }
    
}
