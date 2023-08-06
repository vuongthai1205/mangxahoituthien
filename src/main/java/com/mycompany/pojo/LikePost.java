/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vuongthai1205
 */
@Entity
@Table(name = "like_post")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LikePost.findAll", query = "SELECT l FROM LikePost l"),
    @NamedQuery(name = "LikePost.findById", query = "SELECT l FROM LikePost l WHERE l.id = :id"),
    @NamedQuery(name = "LikePost.findByIsLike", query = "SELECT l FROM LikePost l WHERE l.isLike = :isLike"),
    @NamedQuery(name = "LikePost.findByCreateAt", query = "SELECT l FROM LikePost l WHERE l.createAt = :createAt"),
    @NamedQuery(name = "LikePost.findByUpdateAt", query = "SELECT l FROM LikePost l WHERE l.updateAt = :updateAt")})
public class LikePost implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_like")
    private Short isLike;
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @JoinColumn(name = "id_post", referencedColumnName = "id")
    @ManyToOne
    private Post idPost;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne
    private User idUser;

    public LikePost() {
    }

    public LikePost(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getIsLike() {
        return isLike;
    }

    public void setIsLike(Short isLike) {
        this.isLike = isLike;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Post getIdPost() {
        return idPost;
    }

    public void setIdPost(Post idPost) {
        this.idPost = idPost;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
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
        if (!(object instanceof LikePost)) {
            return false;
        }
        LikePost other = (LikePost) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.LikePost[ id=" + id + " ]";
    }
    
}
