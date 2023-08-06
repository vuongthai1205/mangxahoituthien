/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.pojo.AuctionStatus;
import com.mycompany.repository.AuctionStatusRepository;
import com.mycompany.service.AuctionStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service
public class AuctionStatusServiceImpl implements AuctionStatusService{

    @Autowired
    private AuctionStatusRepository auctionStatusRepository;
    
    @Override
    public List<AuctionStatus> getAuctionStatuses() {
        return this.auctionStatusRepository.getAuctionStatuses();
    }
    
}
