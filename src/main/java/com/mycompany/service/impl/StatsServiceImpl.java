/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.service.impl;

import com.mycompany.repository.StatsRepository;
import com.mycompany.service.StatsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author vuongthai1205
 */
@Service
public class StatsServiceImpl implements  StatsService{
    @Autowired
    private StatsRepository statsRepository;

    @Override
    public List<Object[]> stats(Map<String, String> params) {
        return this.statsRepository.stats(params);
    }
    
}
