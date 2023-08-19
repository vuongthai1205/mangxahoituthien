/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.DTO.LoginResponse;
import com.mycompany.components.JwtService;
import com.mycompany.pojo.AuthenticationRequest;
import com.mycompany.pojo.User;
import com.mycompany.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vuongthai1205
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping("/login/")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        if (this.userService.authUser(authenticationRequest.getUsername(), authenticationRequest.getPassword()) == true) {
            String token = this.jwtService.generateTokenLogin(authenticationRequest.getUsername());
            return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
        }

        return new ResponseEntity<>(new LoginResponse("error"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.userService.getListUser(params), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "id") int id) {
        this.userService.deleteUser(id);
    }

    @PostMapping(path = "/user/add/")
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        // Lưu thông tin user vào userService

        this.userService.addOrUpdateUser(user);
    }
    
    @GetMapping(path = "/current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<User> details(Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

}
