/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.pojo.HashTag;
import com.mycompany.repository.TagRepository;
import com.mycompany.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service
public class TagServiceImpl implements TagService{
    @Autowired
    private TagRepository tagRepository;

    @Override
    public boolean addOrUpdateTag(HashTag hashTag) {
        return this.tagRepository.addOrUpdateTag(hashTag);
    }

    @Override
    public boolean checkTag(String content) {
        return this.tagRepository.checkTag(content);
    }

    @Override
    public HashTag getTagByContent(String content) {
        return this.tagRepository.getTagByContent(content);
    }

    @Override
    public HashTag getTagById(int id) {
        return this.tagRepository.getTagById(id);
    }

    @Override
    public boolean deleteTag(HashTag hashTag) {
        return this.tagRepository.deleteTag(hashTag);
    }
    
}
