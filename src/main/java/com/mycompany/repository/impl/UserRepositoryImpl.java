/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.User;
import com.mycompany.repository.UserRepository;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
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
public class UserRepositoryImpl implements UserRepository{

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public List<User> getUsers(String name) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();

        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root root = q.from(User.class);
        q.select(root);
        
        if (!name.isEmpty()) {
            Predicate p = b.equal(root.get("username").as(String.class), name.trim());
            q = q.where(p);
        }
        
        Query query = session.createQuery(q);
         
        return query.getResultList();
    }

    @Override
    public void addOrUpdateUser(User user) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();
        try {
            if (user.getId() == null) {
                session.save(user);
            } else {
                session.update(user);
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }
    
}
