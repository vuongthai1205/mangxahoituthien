/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mycompany.pojo.Role;
import com.mycompany.pojo.User;
import com.mycompany.pojo.UserRole;
import com.mycompany.repository.RoleRepository;
import com.mycompany.repository.UserRepository;
import com.mycompany.repository.UserRoleRepository;
import com.mycompany.service.PostService;
import com.mycompany.service.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
@Autowired
    private Cloudinary cloudinary;
    @Override
    public List<User> getUsers(String name) {
        return this.userRepository.getUsers(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.getUsers(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Không tồn tại!");
        }
        User u = users.get(0);
        Set<GrantedAuthority> authorities = new HashSet<>();
        
        List<UserRole> listUserRoles = userRoleRepository.getUserRole(u);
        listUserRoles.forEach(userRole -> {
            Role role = roleRepository.getRole(userRole.getIdRole().getId());
            authorities.add(new SimpleGrantedAuthority(role.getNameRole()));
        });
        
        
        return new org.springframework.security.core.userdetails.User(
                u.getUserName(), u.getPassword(), authorities);
    }

    @Override
    public boolean addOrUpdateUser(User user) {
        String pass = user.getPassword();
        if (!user.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        user.setPassword(passwordEncoder.encode(pass));
        Date date = new Date();
        user.setCreateAt(date);
        
        // Lưu user vào cơ sở dữ liệu trước khi tạo userRole
        this.userRepository.addOrUpdateUser(user);
        // Lấy role từ repository hoặc cách nào bạn đã cấu hình
        Role role = this.roleRepository.getRole(2);

        // Tạo và gán userRole
        UserRole userRole = new UserRole();
        userRole.setIdUser(user);  // Gán user đã lưu
        userRole.setIdRole(role);

        // Thêm userRole vào cơ sở dữ liệu
        return this.userRoleRepository.addUserRole(userRole);
    }

}