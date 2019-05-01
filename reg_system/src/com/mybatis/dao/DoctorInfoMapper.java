package com.mybatis.dao;

import com.mybatis.model.DoctorInfo;
import com.mybatis.model.DoctorInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DoctorInfoMapper {
    long countByExample(DoctorInfoExample example);

    int deleteByExample(DoctorInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(DoctorInfo record);

    int insertSelective(DoctorInfo record);

    List<DoctorInfo> selectByExample(DoctorInfoExample example);

    DoctorInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DoctorInfo record, @Param("example") DoctorInfoExample example);

    int updateByExample(@Param("record") DoctorInfo record, @Param("example") DoctorInfoExample example);

    int updateByPrimaryKeySelective(DoctorInfo record);

    int updateByPrimaryKey(DoctorInfo record);
}