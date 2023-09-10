/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.pojo.ReportUser;
import com.mycompany.pojo.User;
import com.mycompany.repository.ReportUserRepository;
import com.mycompany.service.ReportUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service
public class ReportUserServiceImpl implements ReportUserService{
    @Autowired
    private ReportUserRepository reportUserRepository;

    @Override
    public boolean addReport(ReportUser reportUser) {
        return this.reportUserRepository.addReport(reportUser);
    }

    @Override
    public List<ReportUser> getListReportUsers(User user) {
        return this.reportUserRepository.getListReportUsers(user);
    }

    @Override
    public ReportUser getReportUserById(int id) {
        return this.reportUserRepository.getReportUserById(id);
    }

    @Override
    public ReportUser getReportUser(User user, User userReported) {
        return this.reportUserRepository.getReportUser(user, userReported);
    }

    @Override
    public boolean updateReport(ReportUser reportUser) {
        return this.reportUserRepository.updateReport(reportUser);
    }
    
}
