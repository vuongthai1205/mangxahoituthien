/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mycompany.pojo.Post;
import com.mycompany.repository.PostRepository;
import com.mycompany.service.PostService;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Post> getPostList(Map<String, String> params) {
        return this.postRepository.getPostList(params);
    }

    @Override
    public Post getPostById(int id) {
        return this.postRepository.getPostById(id);
    }

    @Override
    public boolean addOrUpdatePost(Post post) {
        Date currentDate = new Date();

        if (post.getId() == null) {
            // This is a new post
            post.setCreateAt(currentDate);
        }

        if (post.getFile() != null && !post.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(post.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                post.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                // Handle the exception appropriately, e.g., throw a custom exception or return an error response
                Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, "Error uploading image to Cloudinary", ex);
            }
        }

        post.setUpdateAt(currentDate);

        return this.postRepository.addOrUpdatePost(post);
    }

    @Override
    public boolean deletePost(int id) {
        return this.postRepository.deletePost(id);
    }

}
