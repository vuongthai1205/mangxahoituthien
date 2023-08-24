/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.pojo.LikePost;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.repository.LikeRepository;
import com.mycompany.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service
public class LikeServiceImpl implements LikeService{
    
    @Autowired
    private LikeRepository likeRepository;
    @Override
    public boolean addLike(LikePost likePost) {
        return this.likeRepository.addLike(likePost);
    }

    @Override
    public boolean checkUserLiked(User user, Post post) {
        return this.likeRepository.checkUserLiked(user, post);
    }

    @Override
    public LikePost getLikePost(User user, Post post) {
        return this.likeRepository.getLikePost(user, post);
    }
    
}
