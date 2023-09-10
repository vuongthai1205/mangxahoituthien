/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

import com.mycompany.pojo.ReportUser;
import com.mycompany.pojo.User;
import java.util.List;

/**
 *
 * @author vuongthai1205
 */
public interface ReportUserRepository {
    boolean addReport(ReportUser reportUser);
    boolean updateReport(ReportUser reportUser);
    List<ReportUser> getListReportUsers(User user);
    ReportUser getReportUserById(int id);
    ReportUser getReportUser(User user, User userReported);
}
