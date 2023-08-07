/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.Role;
import com.mycompany.repository.RoleRepository;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class RoleRepositoryImpl implements RoleRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Override
    public Role getRole(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Role.class, id);
    }

    @Override
    public List<Role> getListRoles() {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        
        Root<Role> root = criteriaQuery.from(Role.class);
        criteriaQuery.select(root);
        
        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    } 
    
}
