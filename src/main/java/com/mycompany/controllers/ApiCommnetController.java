/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.DTO.CommentRequestDTO;
import com.mycompany.pojo.Comment;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.service.CommentService;
import com.mycompany.service.PostService;
import com.mycompany.service.UserService;
import java.security.Principal;
import javax.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dinht
 */
@Controller
@CrossOrigin
@RequestMapping("/api")
public class ApiCommnetController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/post/comment/{id}/")
    public ResponseEntity<String> addComment(Principal user, @PathVariable(value = "id") int id, @RequestBody CommentRequestDTO commentRequestDTO) {
        User u = this.userService.getUserByUsername(user.getName());
        Post post = this.postService.getPostById(id);

        Comment newComment = new Comment();
        newComment.setIdPost(post);
        newComment.setIdUser(u);
        newComment.setContent(commentRequestDTO.getContent());
        if (this.commentService.addComment(newComment)) {
            return new ResponseEntity<>("Cmt", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("comment loi", HttpStatus.BAD_REQUEST);
    }
}
