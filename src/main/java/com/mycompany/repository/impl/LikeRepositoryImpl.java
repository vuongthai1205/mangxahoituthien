/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.LikePost;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.repository.LikeRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vuongthai1205
 */
@Repository
@Transactional
public class LikeRepositoryImpl implements LikeRepository{
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public boolean addLike(LikePost likePost) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        try{
            if (likePost.getId() == null) {
                session.save(likePost);
            }
            else{
                session.update(likePost);
            }
            
            return true;
        }
        catch(HibernateException ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkUserLiked(User user, Post post) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<LikePost> criteriaQuery = criteriaBuilder.createQuery(LikePost.class);
        
        Root<LikePost> root = criteriaQuery.from(LikePost.class);
        
        criteriaQuery.select(root).where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("idPost"), post),
                criteriaBuilder.equal(root.get("idUser"), user)
            ));
        LikePost existingLike = session.createQuery(criteriaQuery).uniqueResult();
        
        if (existingLike != null) {
            return true;
        }
        
        return false;
        
        
    }

    @Override
    public LikePost getLikePost(User user, Post post) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<LikePost> criteriaQuery = criteriaBuilder.createQuery(LikePost.class);
        
        Root<LikePost> root = criteriaQuery.from(LikePost.class);
        
        criteriaQuery.select(root).where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("idPost"), post),
                criteriaBuilder.equal(root.get("idUser"), user)
            ));
        LikePost existingLike = session.createQuery(criteriaQuery).uniqueResult();
        
        return existingLike;
    }

    @Override
    public List<LikePost> getLikePosts(Post post) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<LikePost> criteriaQuery = criteriaBuilder.createQuery(LikePost.class);
        
        Root<LikePost> root = criteriaQuery.from(LikePost.class);
        
        criteriaQuery.select(root).where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("idPost"), post),
                criteriaBuilder.equal(root.get("isLike"), 1)
        ));
        
        Query q = session.createQuery(criteriaQuery);
        
        return q.getResultList();
    }
    
}
