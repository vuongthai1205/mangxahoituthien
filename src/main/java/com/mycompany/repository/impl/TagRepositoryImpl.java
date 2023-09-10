/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.HashTag;
import com.mycompany.repository.TagRepository;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
public class TagRepositoryImpl implements TagRepository{
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public boolean addOrUpdateTag(HashTag hashTag) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        
        try {
            if(hashTag.getId() == null){
                session.save(hashTag);
            }
            else{
                session.update(hashTag);
            }
            return true;
            
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkTag(String content) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<HashTag> criteriaQuery = criteriaBuilder.createQuery(HashTag.class);
        Root root = criteriaQuery.from(HashTag.class);
        
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("content"), content));
        
        HashTag hashTag = session.createQuery(criteriaQuery).uniqueResult();
        
        if (hashTag != null) {
            return true;
        }
        
        return false;
    }

    @Override
    public HashTag getTagByContent(String content) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<HashTag> criteriaQuery = criteriaBuilder.createQuery(HashTag.class);
        Root root = criteriaQuery.from(HashTag.class);
        
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("content"), content));
        
        HashTag hashTag = session.createQuery(criteriaQuery).uniqueResult();
        
        return hashTag;
    }

    @Override
    public HashTag getTagById(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return session.get(HashTag.class, id);
    }

    @Override
    public boolean deleteTag(HashTag hashTag) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();
        try {
            session.delete(hashTag);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
}
