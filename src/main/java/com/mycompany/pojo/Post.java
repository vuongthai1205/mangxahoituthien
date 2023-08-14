/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author vuongthai1205
 */
@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@Data
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "title")
    private String title;
    @Lob
    @Size(max = 65535)
    @Column(name = "content")
    private String content;
    @Size(max = 150)
    @Column(name = "image")
    private String image;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "start_price")
    private Double startPrice;
    @Column(name = "auction_start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auctionStartTime;
    @Size(max = 45)
    @Column(name = "auction_end_time")
    private String auctionEndTime;
    @Column(name = "auction_winner_id")
    private Integer auctionWinnerId;
    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Column(name = "update_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
    @JoinColumn(name = "auction_status", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private AuctionStatus auctionStatus;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User idUser;
    @OneToMany(mappedBy = "idPost")
    @JsonIgnore
    private Collection<LikePost> likePostCollection;
    @OneToMany(mappedBy = "idPost")
    @JsonIgnore
    private Collection<Comment> commentCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPost")
    @JsonIgnore
    private Collection<ResultAction> resultActionCollection;
    @OneToMany(mappedBy = "idPost")
    @JsonIgnore
    private Collection<Share> shareCollection;
    
    @OneToMany(mappedBy = "idPost")
    @JsonIgnore
    private Collection<PostTag> postTagCollection;
    
    @Transient
    @JsonIgnore
    private MultipartFile file;


    public Post(Integer id) {
        this.id = id;
    }

    

    @XmlTransient
    public Collection<LikePost> getLikePostCollection() {
        return likePostCollection;
    }

    public void setLikePostCollection(Collection<LikePost> likePostCollection) {
        this.likePostCollection = likePostCollection;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @XmlTransient
    public Collection<ResultAction> getResultActionCollection() {
        return resultActionCollection;
    }

    public void setResultActionCollection(Collection<ResultAction> resultActionCollection) {
        this.resultActionCollection = resultActionCollection;
    }

    @XmlTransient
    public Collection<Share> getShareCollection() {
        return shareCollection;
    }

    public void setShareCollection(Collection<Share> shareCollection) {
        this.shareCollection = shareCollection;
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
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.pojo.Post[ id=" + id + " ]";
    }

    
}
