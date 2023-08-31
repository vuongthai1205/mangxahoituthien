/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.pojo.Auction;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import com.mycompany.repository.AuctionRepository;
import com.mycompany.service.AuctionService;
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

    @Override
    public boolean addOrUpdateAuction(Auction auction) {
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
    
}
