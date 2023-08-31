/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.Role;
import com.mycompany.pojo.User;
import com.mycompany.repository.RoleRepository;
import com.mycompany.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vuongthai1205
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

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
                if (user.getRole() == null) {
                    Set<Role> roles = new HashSet<>();
                    Role role = this.roleRepository.getRole(2);
                    roles.add(role);
                    user.setRoles(roles);
                } else {
                    Set<Role> roles = new HashSet<>();
                    Role role = this.roleRepository.getRole(user.getRole().getId());
                    roles.add(role);
                    user.setRoles(roles);
                }

                session.save(user);
            } else {
                Set<Role> roles = new HashSet<>();
                Role role = this.roleRepository.getRole(user.getRole().getId());
                roles.add(role);
                user.setRoles(roles);
                session.update(user);
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> getListUser(Map<String, String> params) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root root = criteriaQuery.from(User.class);

        criteriaQuery.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("username"), String.format("%%%s%%", kw)));
            }
            criteriaQuery.where(predicates.toArray(Predicate[]::new));
        }

        Query query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }

    @Override
    public User getUserById(int id) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public boolean deleteUser(int id) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();
        User user = this.getUserById(id);
        try {
            Query deleteQuery = session.createQuery("DELETE FROM UserRole ur WHERE ur.idUser = :user");
            deleteQuery.setParameter("user", user);
            deleteQuery.executeUpdate();
            session.delete(user);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUserByUsername(String username) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("username"), username));

        return session.createQuery(criteriaQuery).uniqueResult();
    }

    @Override
    public boolean authUser(String username, String password) {
        User u = this.getUserByUsername(username);

        return this.passEncoder.matches(password, u.getPassword());
    }

}
