/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

import com.mycompany.pojo.Post;
import java.util.List;
import java.util.Map;

/**
 *
 * @author vuongthai1205
 */
public interface PostRepository {
    List<Post> getPostList(Map<String, String> params);
    Post getPostById(int id);
    boolean addOrUpdatePost(Post post);
    boolean deletePost(int id);
}
