/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

import com.mycompany.pojo.Comment;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import java.util.List;

/**
 *
 * @author vuongthai1205
 */
public interface CommentRepository {

    boolean addComment(Comment comment);

    boolean checkUserComment(User user, Post post);

    Comment getCommentPost(User user, Post post);

    List<Comment> listCommentPost(Post post);
    
    Comment getCommentById(int id);
    boolean deleteComment(Comment comment);
}
