/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service;

import com.mycompany.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author vuongthai1205
 */
public interface UserService extends UserDetailsService{
    List<User> getUsers(String name);
    boolean addOrUpdateUser(User user);
    List<User> getListUser(Map<String, String> params);
    User getUserById(int id);
    boolean deleteUser(int id);
    int checkUser(String username, String password);
    User getUserByUsername(String username);
}