/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

import com.mycompany.pojo.AuctionStatus;
import java.util.List;

/**
 *
 * @author vuongthai1205
 */
public interface AuctionStatusRepository {
    List<AuctionStatus> getAuctionStatuses();
    AuctionStatus getAuctionStatus(int id);
}
