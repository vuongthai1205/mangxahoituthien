/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

import com.mycompany.pojo.User;
import java.util.List;

/**
 *
 * @author vuongthai1205
 */
public interface UserRepository {
    List<User> getUsers(String name);
    void addOrUpdateUser(User user);
}