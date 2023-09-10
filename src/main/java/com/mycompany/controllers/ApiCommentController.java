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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dinht
 */
@Controller
@CrossOrigin
@RequestMapping("/api")
public class ApiCommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/post/comment/{id-post}/")
    public ResponseEntity<String> addComment(Principal user, @PathVariable(value = "id-post") int id, @RequestBody CommentRequestDTO commentRequestDTO) {
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
    
    @PutMapping("/post/comment/{id}/")
    public ResponseEntity<String> updateComment(Principal user, @PathVariable(value = "id") int id, @RequestBody CommentRequestDTO commentRequestDTO){
        User u = this.userService.getUserByUsername(user.getName());
        Comment comment = this.commentService.getCommentById(id);
        
        if (comment.getIdUser().equals(u)) {
            comment.setContent(commentRequestDTO.getContent());
            if (this.commentService.addComment(comment)) {
                return new ResponseEntity<>("comment thanh cong", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("comment loi", HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("k có quyen sua comment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/post/comment/{id}/")
    public ResponseEntity<String> deleteComment(Principal user, @PathVariable(value = "id") int id){
        User u = this.userService.getUserByUsername(user.getName());
        Comment comment = this.commentService.getCommentById(id);
        
        if (comment.getIdUser().equals(u)) {
            if (this.commentService.deleteComment(comment)) {
                return new ResponseEntity<>("xóa comment thanh cong", HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("comment loi", HttpStatus.BAD_REQUEST);
            }
        }
        else{
            return new ResponseEntity<>("k có quyen xóa comment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
