/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vuongthai1205
 */
@Entity
@Table(name = "post_tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PostTag.findAll", query = "SELECT p FROM PostTag p"),
    @NamedQuery(name = "PostTag.findById", query = "SELECT p FROM PostTag p WHERE p.id = :id")})
public class PostTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_tag", referencedColumnName = "id")
    @ManyToOne
    private HashTag idTag;
    @JoinColumn(name = "id_post", referencedColumnName = "id")
    @ManyToOne
    private Post idPost;

    public PostTag() {
    }

    public PostTag(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public HashTag getIdTag() {
        return idTag;
    }

    public void setIdTag(HashTag idTag) {
        this.idTag = idTag;
    }

    public Post getIdPost() {
        return idPost;
    }

    public void setIdPost(Post idPost) {
        this.idPost = idPost;
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
        if (!(object instanceof PostTag)) {
            return false;
        }
        PostTag other = (PostTag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.PostTag[ id=" + id + " ]";
    }
    
}
