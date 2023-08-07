/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.Role;
import com.mycompany.pojo.User;
import com.mycompany.pojo.UserRole;
import com.mycompany.repository.UserRoleRepository;
import java.util.List;
import javax.persistence.Query;
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
public class UserRoleRepositoryImpl implements UserRoleRepository{
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public boolean addUserRole(UserRole userRole) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            
            if (userRole.getId() == null) {
                session.save(userRole);
                return true;
            }
            else{
                session.update(userRole);
                return true;
            }
            
            
            

            
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
        
        
        
    }

    @Override
    public List<UserRole> getUserRole(User user) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<UserRole> criteriaQuery  = b.createQuery(UserRole.class);
        Root root = criteriaQuery.from(UserRole.class);
        criteriaQuery.select(root);
        
        criteriaQuery.where(b.equal(root.get("idUser"), user));
        
        Query query = session.createQuery(criteriaQuery );
        return query.getResultList();
    }

    @Override
    public UserRole getUserRoleByUser(User user) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<UserRole> criteriaQuery  = b.createQuery(UserRole.class);
        Root root = criteriaQuery.from(UserRole.class);
        criteriaQuery.select(root);
        
        criteriaQuery.where(b.equal(root.get("idUser"), user));
        
        Query query = session.createQuery(criteriaQuery );
        return (UserRole) query.getSingleResult();
    }
    
}
