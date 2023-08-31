/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.DTO.AuctionRequestDTO;
import com.mycompany.DTO.AuctionResponseDTO;
import com.mycompany.pojo.Auction;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.service.AuctionService;
import com.mycompany.service.PostService;
import com.mycompany.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vuongthai1205
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiAuctionController {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/auction/")
    public ResponseEntity<String> addAuction(Principal user, @RequestBody AuctionRequestDTO auctionRequestDTO) {
        User u = this.userService.getUserByUsername(user.getName());
        Post post = this.postService.getPostById(auctionRequestDTO.getIdPost());

        if (this.auctionService.checkAuctionExist(u, post)) {
            return new ResponseEntity<>("Ban da dau gia bai viet", HttpStatus.CONFLICT);
        }
        if (auctionRequestDTO.getPrice() < post.getStartPrice()) {
            return new ResponseEntity<>("Vui lòng cho giá cao hon giá khoi diem", HttpStatus.BAD_REQUEST);
        } else {
            Auction auction = new Auction();
            auction.setIdUser(u);
            auction.setIdPost(post);
            auction.setPrice(auctionRequestDTO.getPrice());
            if (this.auctionService.addOrUpdateAuction(auction)) {

                return new ResponseEntity<>("Thành Công", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Không thành công ", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    
    @GetMapping("/auction/{id}/")
    public ResponseEntity<?> getAuctions (Principal user, @PathVariable(value = "id") int id){
        User u = this.userService.getUserByUsername(user.getName());
        Post post =this.postService.getPostById(id);
        if (post.getIdUser().equals(u)){
            List<Auction> auctions = this.auctionService.getListAuction(post);
            List<AuctionResponseDTO> auctionResponseDTOs = new ArrayList<>(); 
            auctions.forEach(a -> {
                AuctionResponseDTO auctionResponseDTO = new AuctionResponseDTO();
                auctionResponseDTO.setUsername(a.getIdUser().getUsername());
                auctionResponseDTO.setAvatar(a.getIdUser().getAvatar());
                auctionResponseDTO.setPrice(a.getPrice());
                
                auctionResponseDTOs.add(auctionResponseDTO);
            });
            
            
            return new ResponseEntity<>(auctionResponseDTOs, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Ban khong phai chu so huu bai viet", HttpStatus.BAD_REQUEST);
        }
       
    }
}
