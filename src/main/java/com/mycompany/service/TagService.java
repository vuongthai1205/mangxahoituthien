/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.pojo.HashTag;

/**
 *
 * @author vuongthai1205
 */
public interface TagService {
    boolean addOrUpdateTag(HashTag hashTag);
    boolean checkTag(String content);
    HashTag getTagByContent(String content);
    HashTag getTagById(int id);
    boolean deleteTag(HashTag hashTag);
}
