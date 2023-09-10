/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.repository.PostRepository;
import com.mycompany.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author vuongthai1205
 */
@Repository
@Transactional
public class PostRepositoryImpl implements PostRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment env;

    @Override
    public List<Post> getPostList(Map<String, String> params) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();

        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Post> q = b.createQuery(Post.class);
        Root root = q.from(Post.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("title"), String.format("%%%s%%", kw)));
            }

            String iduser = params.get("iduser");
            // Lấy tham số iduser từ Map
            
            if (iduser != null && !iduser.isEmpty()) {
                User u = this.userRepository.getUserById(Integer.parseInt(iduser));
                predicates.add(b.equal(root.get("idUser"), u)); // Thêm điều kiện lọc theo iduser
            }

            if (!predicates.isEmpty()) {
                q.where(predicates.toArray(Predicate[]::new));
            }
        }

        q.orderBy(b.desc(root.get("createAt")));

        Query query = session.createQuery(q);
        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int p = Integer.parseInt(page);
                int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));

                if (p > 0) {  // Kiểm tra nếu page > 0 thì áp dụng giới hạn và vị trí bắt đầu
                    query.setMaxResults(pageSize);
                    query.setFirstResult((p - 1) * pageSize);
                }
            }
        }
        return query.getResultList();
    }

    @Override
    public Post getPostById(int id) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();
        return session.get(Post.class, id);
    }

    @Override
    public boolean addOrUpdatePost(Post post) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();
        try {
            if (post.getId() == null) {
                session.save(post);
            } else {
                session.update(post);
            }

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePost(int id) {
        Session session = this.sessionFactory
                .getObject()
                .getCurrentSession();
        Post post = this.getPostById(id);
        try {
            session.delete(post);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public int countPost() {
        Session s = this.sessionFactory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) FROM Post");
        return Integer.parseInt(q.getSingleResult().toString());
    }

}
