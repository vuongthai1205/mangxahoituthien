/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.pojo.User;
import com.mycompany.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author vuongthai1205
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String signin(Model model,@ModelAttribute(value = "user") User user){
        String errMsg;
        if (user.getRepeatPassword().equals(user.getPassword())) {
            if (this.userService.addOrUpdateUser(user) == true) {
                return "redirect:/login";
            }
            else{
                errMsg = "Has error";
            }
            
        }
        else{
            errMsg = "Password not match";
        }
        
        model.addAttribute("errMsg", errMsg);
        
        return "register";
    }
}
