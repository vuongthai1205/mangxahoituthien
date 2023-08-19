/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.DTO;

import java.util.Date;
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
    private String title;
    private String content;
    private String image;
    private UserResponseDTO user;
    private Date createAt;
    private Date updateAt;
}
