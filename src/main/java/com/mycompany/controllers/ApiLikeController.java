/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.pojo.LikePost;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.service.LikeService;
import com.mycompany.service.PostService;
import com.mycompany.service.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author vuongthai1205
 */
@Controller
@CrossOrigin
@RequestMapping("/api")
public class ApiLikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/post/like/{id}/")
    public ResponseEntity<String> addLike(Principal user, @PathVariable(value = "id") int id) {
        User u = this.userService.getUserByUsername(user.getName());
        Post post = this.postService.getPostById(id);
        if (this.likeService.checkUserLiked(u, post) == true) {
            LikePost likePost = this.likeService.getLikePost(u, post);
            if (likePost.getIsLike() == 1) {
                likePost.setIsLike(false);
            }
            else{
                likePost.setIsLike(true);
            }
            if (this.likeService.addLike(likePost) == true) {
                return new ResponseEntity<>("Liked", HttpStatus.CREATED);
            }
        } else {
            LikePost likePost = new LikePost();
            likePost.setIdPost(post);
            likePost.setIdUser(u);
            likePost.setIsLike(true);
            if (this.likeService.addLike(likePost) == true) {
                return new ResponseEntity<>("Liked", HttpStatus.CREATED);
            }
        }

        return new ResponseEntity<>("Don't like", HttpStatus.BAD_REQUEST);
    }
}
