/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.pojo.Role;
import com.mycompany.pojo.User;
import java.util.List;

/**
 *
 * @author vuongthai1205
 */
public interface RoleService {
    List<Role> getListRoles();
    Role getRole(int id);
    List<Role> getListRolesByUser(User user);
}
