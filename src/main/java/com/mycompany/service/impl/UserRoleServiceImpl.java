/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.pojo.User;
import com.mycompany.pojo.UserRole;
import com.mycompany.repository.UserRoleRepository;
import com.mycompany.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service
public class UserRoleServiceImpl implements UserRoleService{
    
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserRole getUserRoleByUser(User user) {
        return this.userRoleRepository.getUserRoleByUser(user);
    }
    
}
