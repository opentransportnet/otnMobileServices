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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "issue_type", schema="otn_web_app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IssueType.findAll", query = "SELECT i FROM IssueType i"),
    @NamedQuery(name = "IssueType.findByIssueTypeId", query = "SELECT i FROM IssueType i WHERE i.issueTypeId = :issueTypeId"),
    @NamedQuery(name = "IssueType.findByName", query = "SELECT i FROM IssueType i WHERE i.name = :name")})
public class IssueType implements Serializable {
    @JoinColumn(name = "app_id", referencedColumnName = "app_id")
    @ManyToOne
    private Apps appId;
   
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "issue_type_id")
    private Integer issueTypeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "name")
    private String name;

    public IssueType() {
    }

    public IssueType(Integer issueTypeId) {
        this.issueTypeId = issueTypeId;
    }

    public IssueType(Integer issueTypeId, String name) {
        this.issueTypeId = issueTypeId;
        this.name = name;
    }

    public Integer getIssueTypeId() {
        return issueTypeId;
    }

    public void setIssueTypeId(Integer issueTypeId) {
        this.issueTypeId = issueTypeId;
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
        hash += (issueTypeId != null ? issueTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IssueType)) {
            return false;
        }
        IssueType other = (IssueType) object;
        if ((this.issueTypeId == null && other.issueTypeId != null) || (this.issueTypeId != null && !this.issueTypeId.equals(other.issueTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "otn.mobile.persistency.IssueType[ issueTypeId=" + issueTypeId + " ]";
    }

    public Apps getAppId() {
        return appId;
    }

    public void setAppId(Apps appId) {
        this.appId = appId;
    }
    
}
