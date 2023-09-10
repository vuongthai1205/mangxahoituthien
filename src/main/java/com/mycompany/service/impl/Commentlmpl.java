/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.pojo.Comment;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.repository.CommentRepository;
import com.mycompany.service.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dinht
 */
@Service
public class Commentlmpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public boolean addComment(Comment comment) {
        return this.commentRepository.addComment(comment);

    }

    @Override
    public boolean checkUserComment(User user, Post post) {
        return this.commentRepository.checkUserComment(user, post);
    }

    @Override
    public Comment getCommentPost(User user, Post post) {
        return this.commentRepository.getCommentPost(user, post);

    }

    @Override
    public List<Comment> listCommentPost(Post post) {
        return this.commentRepository.listCommentPost(post);
    }

    @Override
    public boolean editComment(Comment cmt, String newCmt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean deleteComment(Comment delcomment) {
        return this.commentRepository.deleteComment(delcomment);
    }

    @Override
    public Comment getCommentById(int id) {
        return this.commentRepository.getCommentById(id);
    }
    
}
