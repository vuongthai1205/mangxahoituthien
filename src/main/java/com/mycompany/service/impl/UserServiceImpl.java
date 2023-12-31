/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mycompany.formatter.CustomDateFormatter;
import com.mycompany.pojo.Role;
import com.mycompany.pojo.User;
import com.mycompany.repository.RoleRepository;
import com.mycompany.repository.UserRepository;
import com.mycompany.service.PostService;
import com.mycompany.service.UserService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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

    public static final SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private UserRepository userRepository;

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

        u.getRoles().forEach(item -> {
            authorities.add(new SimpleGrantedAuthority(item.getNameRole()));
        });

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public boolean addOrUpdateUser(User user) {
        if (user.getId() == null) {
            String pass = user.getPassword();
            if (user.getFile() != null && !user.getFile().isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    user.setAvatar(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            user.setPassword(passwordEncoder.encode(pass));
            if (user.getDateString() != null) {
                try {
                    Date dateOfBirth = F.parse(user.getDateString());
                    user.setDateOfBirth(dateOfBirth);
                } catch (ParseException ex) {
                    Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Lưu user vào cơ sở dữ liệu trước khi tạo userRole
            this.userRepository.addOrUpdateUser(user);
            // Lấy role từ repository hoặc cách nào bạn đã cấu hình
            return true;
        } else {
            if (!user.getFile().isEmpty()) {
                try {
                    Map res = this.cloudinary.uploader().upload(user.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                    user.setAvatar(res.get("secure_url").toString());
                } catch (IOException ex) {
                    Logger.getLogger(PostService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            this.userRepository.addOrUpdateUser(user);
            return true;
        }
    }

    @Override
    public List<User> getListUser(Map<String, String> params) {
        return this.userRepository.getListUser(params);
    }

    @Override
    public User getUserById(int id) {
        User user = this.userRepository.getUserById(id);
        return user;
    }

    @Override
    public boolean deleteUser(int id) {
        return this.userRepository.deleteUser(id);
    }

    @Override
    public int checkUser(String username, String password) {
        List<User> users = userRepository.getUsers(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Không tồn tại!");
        }
        User u = users.get(0);
        if (u != null) {
            if (passwordEncoder.matches(password, u.getPassword()) == true) {
                return 1;
            } else {
                return 2;
            }

        }
        return 0;
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    @Override
    public boolean authUser(String username, String password) {
        return this.userRepository.authUser(username, password);
    }

}
