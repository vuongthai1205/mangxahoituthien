/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.pojo.User;
import com.mycompany.pojo.UserRole;

/**
 *
 * @author vuongthai1205
 */

public interface UserRoleService {
    UserRole getUserRoleByUser(User user);
}
