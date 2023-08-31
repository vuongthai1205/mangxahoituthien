/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vuongthai1205
 */
@Entity
@Table(name = "auction_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuctionStatus.findAll", query = "SELECT a FROM AuctionStatus a"),
    @NamedQuery(name = "AuctionStatus.findById", query = "SELECT a FROM AuctionStatus a WHERE a.id = :id"),
    @NamedQuery(name = "AuctionStatus.findByNameAuctionStatus", query = "SELECT a FROM AuctionStatus a WHERE a.nameAuctionStatus = :nameAuctionStatus")})
public class AuctionStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "name_auction_status")
    private String nameAuctionStatus;
    @OneToMany(mappedBy = "auctionStatus")
    @JsonIgnore
    private Collection<Post> postCollection;

    public AuctionStatus() {
    }

    public AuctionStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameAuctionStatus() {
        return nameAuctionStatus;
    }

    public void setNameAuctionStatus(String nameAuctionStatus) {
        this.nameAuctionStatus = nameAuctionStatus;
    }

    @XmlTransient
    public Collection<Post> getPostCollection() {
        return postCollection;
    }

    public void setPostCollection(Collection<Post> postCollection) {
        this.postCollection = postCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuctionStatus)) {
            return false;
        }
        AuctionStatus other = (AuctionStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.AuctionStatus[ id=" + id + " ]";
    }
    
}
