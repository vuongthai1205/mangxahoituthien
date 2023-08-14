 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.DTO.LoginResponse;
import com.mycompany.pojo.AuthenticationRequest;
import com.mycompany.pojo.User;
import com.mycompany.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserService userService;
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.userService.getListUser(params), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(value = "id") int id) {
        this.userService.deleteUser(id);
    }

    @PostMapping(path = "/user/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        // Lưu thông tin user vào userService

        this.userService.addOrUpdateUser(user);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginApi(@RequestBody AuthenticationRequest authenticationRequest) {
        LoginResponse response = new LoginResponse();
        
        switch (this.userService.checkUser(authenticationRequest.getUsername(), authenticationRequest.getPassword())) {
            case 0:
                response.setStatus("Không tồn tại");
                return new ResponseEntity<>(response, HttpStatus.OK);
            case 1:
                User user = this.userService.getUserByUsername(authenticationRequest.getUsername());
                response.setUser(user);
                return new ResponseEntity<>(response, HttpStatus.OK);

            case 2:
                response.setStatus("Sai mật khẩu");
                return new ResponseEntity<>(response, HttpStatus.OK);

            

            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
