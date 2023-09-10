/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author vuongthai1205
 */
public interface StatsRepository {
    List<Object[]> stats(Map<String, String> params);
}
