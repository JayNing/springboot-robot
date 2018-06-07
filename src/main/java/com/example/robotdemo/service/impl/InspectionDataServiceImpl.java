package com.example.robotdemo.service.impl;

import com.example.robotdemo.entity.InspectionData;
import com.example.robotdemo.mapper.InspectionDataMapper;
import com.example.robotdemo.service.InspectionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 机器人service层实现类
 *
 * @author ning
 * @create 2018-06-04 10:03
 **/
@Service("inspectionDataService")
public class InspectionDataServiceImpl implements InspectionDataService {

    @Autowired
    private InspectionDataMapper inspectionDataMapper;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void insert(InspectionData inspectionData) {
        inspectionDataMapper.insert(inspectionData);
    }
}