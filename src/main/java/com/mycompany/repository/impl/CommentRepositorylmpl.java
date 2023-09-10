/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.Comment;
import com.mycompany.pojo.LikePost;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.repository.CommentRepository;
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
 * @author dinht
 */
@Repository
@Transactional
public class CommentRepositorylmpl implements CommentRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public boolean addComment(Comment comment) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        try {
            if (comment.getId() == null) {
                session.save(comment);
            } else {
                session.update(comment);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkUserComment(User user, Post post) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);

        Root<Comment> root = criteriaQuery.from(Comment.class);

        criteriaQuery.select(root).where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("idComment"), post),
                criteriaBuilder.equal(root.get("idUser"), user)
        ));
        Comment existingComment = session.createQuery(criteriaQuery).uniqueResult();
        if (existingComment != null) {
            return true;
        }

        return false;
    }

    @Override
    public Comment getCommentPost(User user, Post post) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Comment> listCommentPost(Post post) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);

        Root<Comment> root = criteriaQuery.from(Comment.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("idPost"), post))
                .orderBy(criteriaBuilder.desc(root.get("createAt"))); // Sắp xếp theo thứ tự mới nhất

        Query<Comment> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Comment getCommentById(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return session.get(Comment.class, id);
    }

    @Override
    public boolean deleteComment(Comment comment) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(comment);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
