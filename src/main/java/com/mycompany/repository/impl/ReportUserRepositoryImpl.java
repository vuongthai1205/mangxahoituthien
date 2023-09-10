/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.ReportUser;
import com.mycompany.pojo.User;
import com.mycompany.repository.ReportUserRepository;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class ReportUserRepositoryImpl implements ReportUserRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public boolean addReport(ReportUser reportUser) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        try {
            
                session.save(reportUser);
            
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ReportUser> getListReportUsers(User user) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ReportUser> criteriaQuery = criteriaBuilder.createQuery(ReportUser.class);
        Root root = criteriaQuery.from(ReportUser.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("idUser"), user));

        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public ReportUser getReportUserById(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return session.get(ReportUser.class, id);
    }

    @Override
    public ReportUser getReportUser(User user, User userReported) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ReportUser> criteriaQuery = criteriaBuilder.createQuery(ReportUser.class);
        Root root = criteriaQuery.from(ReportUser.class);

        criteriaQuery.select(root).where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("idUser"), user),
                criteriaBuilder.equal(root.get("idUserReported"), userReported)));

        ReportUser reportUser = session.createQuery(criteriaQuery).uniqueResult();
        return reportUser;
    }

    @Override
    public boolean updateReport(ReportUser reportUser) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        try {
            
                session.update(reportUser);
            
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

}
