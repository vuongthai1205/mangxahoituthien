/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.Auction;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.repository.AuctionRepository;
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
public class AuctionRepositoryImpl implements AuctionRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public boolean addOrUpdateAuction(Auction auction) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        try {
            if (auction.getId() == null) {
                session.save(auction);
            } else {
                session.update(auction);
            }
            return true;

        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkAuctionExist(User user, Post post) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Auction> criteriaQuery = criteriaBuilder.createQuery(Auction.class);

        Root<Auction> root = criteriaQuery.from(Auction.class);
        criteriaQuery.select(root).where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("idPost"), post),
                criteriaBuilder.equal(root.get("idUser"), user)
        ));
        
        Auction auction = session.createQuery(criteriaQuery).uniqueResult();
        if (auction != null) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public List<Auction> getListAuction(Post post) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Auction> criteriaQuery = criteriaBuilder.createQuery(Auction.class);
        
        Root<Auction> root = criteriaQuery.from(Auction.class);
        
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("idPost") , post));
        Query q = session.createQuery(criteriaQuery);
        
        return q.getResultList();
    }

}
