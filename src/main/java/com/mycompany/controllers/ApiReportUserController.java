/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.DTO.ReportUserResponseDTO;
import com.mycompany.DTO.ReportUserRequestDTO;
import com.mycompany.DTO.UserResponseDTO;
import com.mycompany.pojo.ReportUser;
import com.mycompany.pojo.User;
import com.mycompany.service.ReportUserService;
import com.mycompany.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author vuongthai1205
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiReportUserController {

    @Autowired
    private ReportUserService reportuserService;

    @Autowired
    private UserService userService;

    @PostMapping("/report/")
    public ResponseEntity<Object> addReport(@RequestBody ReportUserRequestDTO reportUserResquestDTO, Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        User uReport = this.userService.getUserById(reportUserResquestDTO.getIdUser());

        ReportUser reportUser = new ReportUser();
        reportUser.setIdUser(u);
        reportUser.setIdUserReported(uReport);
        reportUser.setReportReason(reportUserResquestDTO.getReportReason());
        if (this.reportuserService.addReport(reportUser)) {
            return new ResponseEntity<>("report user successfully", HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Thêm không thành công");
            errorResponse.put("errorCode", "12345");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/report/{id}/")
    public ResponseEntity<?> getReports(@PathVariable(value = "id") int id) {
        User u = this.userService.getUserById(id);

        List<ReportUser> reportUsers = this.reportuserService.getListReportUsers(u);
        List<ReportUserResponseDTO> reportUserResponseDTOs = new ArrayList<>();
        reportUsers.forEach(report -> {
            ReportUserResponseDTO reportUserResponseDTO = new ReportUserResponseDTO();
            UserResponseDTO uResponseDTO = new UserResponseDTO();

            uResponseDTO.setUsername(report.getIdUser().getUsername());
            uResponseDTO.setAvatar(report.getIdUser().getAvatar());
            uResponseDTO.setId(report.getIdUser().getId());
            uResponseDTO.setAddress(report.getIdUser().getAddress());
            uResponseDTO.setCreateAt(report.getIdUser().getCreateAt());
            uResponseDTO.setUpdateAt(report.getIdUser().getUpdateAt());
            uResponseDTO.setDateOfBirth(report.getIdUser().getDateOfBirth());
            uResponseDTO.setEmail(report.getIdUser().getEmail());
            uResponseDTO.setFirstName(report.getIdUser().getFirstName());
            uResponseDTO.setLastName(report.getIdUser().getLastName());
            uResponseDTO.setPhone(report.getIdUser().getPhone());
            uResponseDTO.setGender(report.getIdUser().getGender());

            reportUserResponseDTO.setUser(uResponseDTO);

            UserResponseDTO uResponseDTOReported = new UserResponseDTO();

            uResponseDTOReported.setUsername(report.getIdUserReported().getUsername());
            uResponseDTOReported.setAvatar(report.getIdUserReported().getAvatar());
            uResponseDTOReported.setId(report.getIdUserReported().getId());
            uResponseDTOReported.setAddress(report.getIdUserReported().getAddress());
            uResponseDTOReported.setCreateAt(report.getIdUserReported().getCreateAt());
            uResponseDTOReported.setUpdateAt(report.getIdUserReported().getUpdateAt());
            uResponseDTOReported.setDateOfBirth(report.getIdUserReported().getDateOfBirth());
            uResponseDTOReported.setEmail(report.getIdUserReported().getEmail());
            uResponseDTOReported.setFirstName(report.getIdUserReported().getFirstName());
            uResponseDTOReported.setLastName(report.getIdUserReported().getLastName());
            uResponseDTOReported.setPhone(report.getIdUserReported().getPhone());
            uResponseDTOReported.setGender(report.getIdUserReported().getGender());

            reportUserResponseDTO.setUserReported(uResponseDTOReported);

            reportUserResponseDTO.setReportReason(report.getReportReason());

            reportUserResponseDTOs.add(reportUserResponseDTO);

        });
        return new ResponseEntity<>(reportUserResponseDTOs, HttpStatus.OK);
    }

    @PutMapping("/report/")
    public ResponseEntity<String> updateReport(@RequestBody ReportUserRequestDTO reportUserResquestDTO, Principal user) {
        User u = this.userService.getUserByUsername(user.getName());
        User uReport = this.userService.getUserById(reportUserResquestDTO.getIdUser());

        ReportUser reportUser = this.reportuserService.getReportUser(u, uReport);
        reportUser.setReportReason(reportUserResquestDTO.getReportReason());
        if (this.reportuserService.updateReport(reportUser)) {
            return new ResponseEntity<>("report user successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("report user fail", HttpStatus.BAD_REQUEST);
        }
    }
}
