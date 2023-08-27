/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

import com.mycompany.pojo.LikePost;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import java.util.List;

/**
 *
 * @author vuongthai1205
 */
public interface LikeRepository {
    boolean addLike(LikePost likePost);
    boolean checkUserLiked(User user, Post post);
    LikePost getLikePost(User user, Post post);
    List<LikePost> getLikePosts(Post post);
    List<LikePost> getLikePostsByPost(Post post);
    boolean deleteLikePost(LikePost likePost);
}
