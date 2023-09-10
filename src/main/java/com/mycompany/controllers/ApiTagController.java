/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.DTO.TagRequestDTO;
import com.mycompany.pojo.HashTag;
import com.mycompany.pojo.Post;
import com.mycompany.service.PostService;
import com.mycompany.service.TagService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vuongthai1205
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiTagController {

    @Autowired
    private TagService tagService;
    @Autowired
    private PostService postService;

    @PostMapping("/post/{idPost}/tags/")
    public ResponseEntity<?> addTag(@PathVariable(value = "idPost") int idPost, @RequestBody TagRequestDTO tagRequestDTO) {
        Post post = this.postService.getPostById(idPost);
        if (!this.tagService.checkTag(tagRequestDTO.getContent())) {
            HashTag hashTag = new HashTag();
            hashTag.setContent(tagRequestDTO.getContent());

            post.addTag(hashTag);

            if (this.tagService.addOrUpdateTag(hashTag) && this.postService.addOrUpdatePost(post)) {

                return new ResponseEntity<>("success", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
            }
        } else {
            HashTag hashTag = this.tagService.getTagByContent(tagRequestDTO.getContent());
            post.addTag(hashTag);

            if (this.postService.addOrUpdatePost(post)) {

                return new ResponseEntity<>("success", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
            }
        }

    }

    @GetMapping("/post/{idPost}/tags/")
    public ResponseEntity<?> getTagByPost(@PathVariable(value = "idPost") int id) {
        Post post = this.postService.getPostById(id);
        if (post != null) {
            Set<HashTag> hashTags = post.getHashTags();
            return new ResponseEntity<>(hashTags, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NOT ok", HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/tags/{id}/")
    public ResponseEntity<?> getTag(@PathVariable(value = "id") int id) {
        HashTag hashTag = this.tagService.getTagById(id);
        
        if (hashTag != null) {
            return new ResponseEntity<>(hashTag, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("NOT ok", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/post/{idpost}/tags/{id}/")
    public ResponseEntity<?> deleteTag(@PathVariable(value = "idpost") int idpost, @PathVariable(value = "id") int id) {
        HashTag hashTag = this.tagService.getTagById(id);
        Post post = this.postService.getPostById(idpost);
        if (hashTag != null) {
            post.removeHashTag(hashTag);
            if (this.postService.addOrUpdatePost(post) && this.tagService.deleteTag(hashTag)) {
                
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    
                
            }else{
                return new ResponseEntity<>("Xóa không thành công", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>("Không tìm thấy hashtag", HttpStatus.NOT_FOUND);
        }
    }

}
