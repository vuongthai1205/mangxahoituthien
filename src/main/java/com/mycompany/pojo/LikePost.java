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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author vuongthai1205
 */
@Entity
@Table(name = "like_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LikePost implements Serializable {

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = new Date(System.currentTimeMillis());
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_like")
    private short isLike;
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

    public LikePost(Post post, User user) {
        this.idPost = post;
        this.idUser = user;
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

    public boolean isIsLike() {
        return isLike == 1;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = (short) (isLike ? 1 : 0);
    }

}
