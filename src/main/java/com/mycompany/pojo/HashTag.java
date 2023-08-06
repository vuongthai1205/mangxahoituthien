/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author vuongthai1205
 */
@Entity
@Table(name = "hash_tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HashTag.findAll", query = "SELECT h FROM HashTag h"),
    @NamedQuery(name = "HashTag.findById", query = "SELECT h FROM HashTag h WHERE h.id = :id"),
    @NamedQuery(name = "HashTag.findByContent", query = "SELECT h FROM HashTag h WHERE h.content = :content")})
public class HashTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "content")
    private String content;
    @OneToMany(mappedBy = "idTag")
    private Collection<PostTag> postTagCollection;

    public HashTag() {
    }

    public HashTag(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @XmlTransient
    public Collection<PostTag> getPostTagCollection() {
        return postTagCollection;
    }

    public void setPostTagCollection(Collection<PostTag> postTagCollection) {
        this.postTagCollection = postTagCollection;
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
        if (!(object instanceof HashTag)) {
            return false;
        }
        HashTag other = (HashTag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.HashTag[ id=" + id + " ]";
    }
    
}
