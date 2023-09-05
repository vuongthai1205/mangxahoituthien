/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.pojo.Comment;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author vuongthai1205
 */
public interface CommentService{
    boolean addComment(Comment comment);
    boolean checkUserComment(User user, Post post);
    Comment getCommentPost(User user, Post post);
    List<Comment> listCommentPost(Post post);
    public boolean editComment(Comment cmt,String newCmt);
    boolean deleteComment(Comment delcomment);


}