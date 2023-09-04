/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

import com.mycompany.pojo.Auction;
import com.mycompany.pojo.Post;
import com.mycompany.pojo.User;
import java.util.List;

/**
 *
 * @author vuongthai1205
 */
public interface AuctionRepository {
    boolean addOrUpdateAuction(Auction auction);
    boolean checkAuctionExist(User user, Post post);
    List<Auction> getListAuction(Post post);
    Auction getAuctionById(int id);
    boolean updateListAuction(List<Auction> auctions);
}
