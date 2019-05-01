package com.mybatis.dao;

import com.mybatis.model.PatientInfo;
import com.mybatis.model.PatientInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PatientInfoMapper {
    long countByExample(PatientInfoExample example);

    int deleteByExample(PatientInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(PatientInfo record);

    int insertSelective(PatientInfo record);

    List<PatientInfo> selectByExample(PatientInfoExample example);

    PatientInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PatientInfo record, @Param("example") PatientInfoExample example);

    int updateByExample(@Param("record") PatientInfo record, @Param("example") PatientInfoExample example);

    int updateByPrimaryKeySelective(PatientInfo record);

    int updateByPrimaryKey(PatientInfo record);
}