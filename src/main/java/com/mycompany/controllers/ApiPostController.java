/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.DTO.PostResponseDTO;
import com.mycompany.DTO.UserResponseDTO;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.service.PostService;
import com.mycompany.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vuongthai1205
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiPostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/post/")
    @CrossOrigin
    public ResponseEntity<List<PostResponseDTO>> getPosts(@RequestParam Map<String, String> params) {
        List<PostResponseDTO> postResponseDTOs = new ArrayList<>();
        List<Post> posts = this.postService.getPostList(params);

        posts.forEach(post -> {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            PostResponseDTO postResponseDTO = new PostResponseDTO();
            postResponseDTO.setId(post.getId());
            postResponseDTO.setTitle(post.getTitle());
            postResponseDTO.setContent(post.getContent());
            postResponseDTO.setImage(post.getImage());
            postResponseDTO.setCreateAt(post.getCreateAt());
            postResponseDTO.setUpdateAt(post.getUpdateAt());

            userResponseDTO.setUsername(post.getIdUser().getUsername());
            userResponseDTO.setAvatar(post.getIdUser().getAvatar());

            postResponseDTO.setUser(userResponseDTO);
            postResponseDTOs.add(postResponseDTO);

        });

        return new ResponseEntity<>(postResponseDTOs, HttpStatus.OK);
    }

    @PostMapping(path = "/post/add/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addOrUpdatePost( @RequestBody Post post, Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        post.setIdUser(u);
        boolean isAddedOrUpdated = postService.addOrUpdatePost(post);

        if (isAddedOrUpdated) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Post added or updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add or update post");
        }
    }

    @DeleteMapping("/post/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") int id) {
        this.postService.deletePost(id);
    }

}
