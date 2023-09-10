/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.pojo.Role;
import com.mycompany.pojo.User;
import com.mycompany.repository.RoleRepository;
import com.mycompany.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Role> getListRoles() {
        return this.roleRepository.getListRoles();
    }

    @Override
    public Role getRole(int id) {
        return this.roleRepository.getRole(id);
    }

    @Override
    public List<Role> getListRolesByUser(User user) {
        return this.roleRepository.getListRolesByUser(user);
    }
    
}
