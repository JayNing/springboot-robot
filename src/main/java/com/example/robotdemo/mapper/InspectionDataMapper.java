package com.example.robotdemo.mapper;

import com.example.robotdemo.entity.InspectionData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface InspectionDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InspectionData record);

    int insertSelective(InspectionData record);

    InspectionData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InspectionData record);

    int updateByPrimaryKey(InspectionData record);

    InspectionData selectNewLastUser();

    List<InspectionData> selectByCreateTime(String createTime);
}