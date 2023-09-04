/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.pojo.Auction;
import com.mycompany.pojo.AuctionStatus;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.repository.AuctionRepository;
import com.mycompany.repository.AuctionStatusRepository;
import com.mycompany.repository.PostRepository;
import com.mycompany.service.AuctionService;
import com.mycompany.service.EmailService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service
public class AuctionServiceImpl implements AuctionService{
    
    @Autowired
    private AuctionRepository auctionRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private AuctionStatusRepository auctionStatusRepository;
    
    @Autowired
    private EmailService emailService;

    @Override
    public boolean addOrUpdateAuction(Auction auction) {
        if (auction.getId() == null) {
            auction.setIsWinnerAuction(false);
        }
        else{
            auction.setIsWinnerAuction(true);
            AuctionStatus auctionStatus = this.auctionStatusRepository.getAuctionStatus(3);
            Post post = auction.getIdPost();
            post.setAuctionStatus(auctionStatus);
            
            if (this.updateListAuction(this.getListAuction(post))) {
                
            }
            if (this.postRepository.addOrUpdatePost(post)) {
                
            }
            
        }
        
        return this.auctionRepository.addOrUpdateAuction(auction);
    }

    @Override
    public boolean checkAuctionExist(User user, Post post) {
        return this.auctionRepository.checkAuctionExist(user, post);
    }

    @Override
    public List<Auction> getListAuction(Post post) {
        return this.auctionRepository.getListAuction(post);
    }

    @Override
    public Auction getAuctionById(int id) {
        return this.auctionRepository.getAuctionById(id);
    }

    @Override
    public boolean updateListAuction(List<Auction> auctions) {
        auctions.forEach(auction -> {
            auction.setIsWinnerAuction(false);
        });
        
        return this.auctionRepository.updateListAuction(auctions);
    }
    
}
