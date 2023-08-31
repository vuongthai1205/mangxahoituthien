/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DTO;

import com.mycompany.pojo.AuctionStatus;
import com.mycompany.pojo.LikePost;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author vuongthai1205
 */
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private int id;
    private String title;
    private String content;
    private String image;
    private double startPrice;
    private UserResponseDTO user;
    private AuctionStatusDTO auctionStatus;
    private List<LikePostDTO> likePost;
    private Date createAt;
    private Date updateAt;
}
