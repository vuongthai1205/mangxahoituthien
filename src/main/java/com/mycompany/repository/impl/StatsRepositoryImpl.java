/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository.impl;

import com.mycompany.pojo.Comment;
import com.mycompany.pojo.LikePost;
import com.mycompany.pojo.Post;
import com.mycompany.repository.StatsRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
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
public class StatsRepositoryImpl implements StatsRepository {

    public static final SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Object[]> stats(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root rPost = q.from(Post.class);

        // Tạo subquery để đếm số like cho mỗi bài viết
        Subquery<Long> likeCountSubquery = q.subquery(Long.class);
        Root likeCountRoot = likeCountSubquery.from(LikePost.class);
        likeCountSubquery.select(b.countDistinct(likeCountRoot.get("id")));
        likeCountSubquery.where(
                b.and(
                        b.equal(likeCountRoot.get("idPost"), rPost.get("id")),
                        b.equal(likeCountRoot.get("isLike"), 1)
                )
        );

        // Join comment bằng LEFT JOIN
        Join<Comment, Post> joinComment = rPost.join("commentCollection", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();

        // Điều kiện: Ngày tạo post từ fromDate đến toDate
        if (params != null) {
            String fd = params.get("fromDate");
            if (fd != null && !fd.isEmpty()) {
                try {
                    predicates.add(b.greaterThanOrEqualTo(rPost.get("createAt"), F.parse(fd)));
                } catch (ParseException ex) {
                    Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String td = params.get("toDate");
            if (td != null && !td.isEmpty()) {
                try {
                    predicates.add(b.lessThanOrEqualTo(rPost.get("createAt"), F.parse(td)));
                } catch (ParseException ex) {
                    Logger.getLogger(StatsRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Select và Group by theo ID và Title, và kết hợp với subquery để tính số like
        q.multiselect(rPost.get("id"), rPost.get("title"),
                likeCountSubquery.getSelection(),
                b.countDistinct(joinComment.get("id")));
        q.groupBy(rPost.get("id"), rPost.get("title"));

        // Áp dụng điều kiện
        q.where(predicates.toArray(new Predicate[0]));

        // Sắp xếp theo ID giảm dần
        q.orderBy(b.desc(rPost.get("id")));

        Query query = session.createQuery(q);

        return query.getResultList();
    }

}
