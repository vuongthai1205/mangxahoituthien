/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.pojo.Role;
import com.mycompany.pojo.User;
import com.mycompany.service.RoleService;
import com.mycompany.service.UserService;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author vuongthai1205
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute
    public void commAttr(Model model) {
        model.addAttribute("roles", this.roleService.getListRoles());
    }

    @GetMapping("/user-manager")
    public String userManager(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("users", this.userService.getListUser(params));
        return "user-manager";
    }

    @GetMapping("/add-user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @GetMapping("/detail-user/{id}")
    public String detailUser(Model model, @PathVariable(value = "id") int id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "detail-user";
    }

    @PostMapping("/detail-user")
    public String updateUser(@ModelAttribute(value = "user") @Valid User user, BindingResult rs) {
        if (!rs.hasErrors()) {
            Role role = this.roleService.getRole(user.getRole().getId());
            user.addRole(role);
            if (this.userService.addOrUpdateUser(user) == true) {
                return "redirect:/user-manager";
            }
        }
        return "detail-user";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String signin(Model model, @ModelAttribute(value = "user") User user) {
        String errMsg;
        if (user.getRepeatPassword().equals(user.getPassword())) {
            Role role = this.roleService.getRole(2);
            user.addRole(role);
            if (this.userService.addOrUpdateUser(user) == true) {
                return "redirect:/login";
            } else {
                errMsg = "Has error";
            }

        } else {
            errMsg = "Password not match";
        }

        model.addAttribute("errMsg", errMsg);

        return "register";
    }

}
