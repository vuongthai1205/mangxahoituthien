/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.pojo.Post;
import com.mycompany.service.AuctionStatusService;
import com.mycompany.service.PostService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author vuongthai1205
 */
@Controller
public class PostController {
    
    @Autowired
    PostService postService;
    
    @Autowired
    AuctionStatusService auctionStatusService;
    
    @RequestMapping("/post")
    public String post(Model model, @RequestParam Map<String, String> params){
        
        
        model.addAttribute("posts", this.postService.getPostList(params));
        return "post";
                
    }
    
    @ModelAttribute
    public void commonAttr(Model model) {
        model.addAttribute("auctionStatuses", this.auctionStatusService.getAuctionStatuses());
    }
    
    @GetMapping("/detail-post/{id}")
    public String detailPost(Model model, @PathVariable(value = "id") int id){
        model.addAttribute("post", this.postService.getPostById(id));
        return "detail-post";
    }
    @PostMapping("/detail-post")
    public String updatePost(@ModelAttribute(value = "post") @Valid Post post, 
            BindingResult rs){
        if(!rs.hasErrors()){
            if(this.postService.addOrUpdatePost(post) == true)
                return "redirect:/post";
        }
        return "login";
    }
}
