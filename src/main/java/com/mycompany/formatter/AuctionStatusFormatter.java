/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.formatter;

import com.mycompany.pojo.AuctionStatus;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author vuongthai1205
 */
public class AuctionStatusFormatter implements Formatter<AuctionStatus>{

    @Override
    public String print(AuctionStatus auctionStatus, Locale locale) {
        return String.valueOf(auctionStatus.getId());
    }

    @Override
    public AuctionStatus parse(String text, Locale locale) throws ParseException {
        int id = Integer.parseInt(text);
        return new AuctionStatus(id);
    }
    
}
