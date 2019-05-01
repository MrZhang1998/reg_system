package com.mybatis.dao;

import com.mybatis.model.DepartmentInfo;
import com.mybatis.model.DepartmentInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DepartmentInfoMapper {
    long countByExample(DepartmentInfoExample example);

    int deleteByExample(DepartmentInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(DepartmentInfo record);

    int insertSelective(DepartmentInfo record);

    List<DepartmentInfo> selectByExample(DepartmentInfoExample example);

    DepartmentInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") DepartmentInfo record, @Param("example") DepartmentInfoExample example);

    int updateByExample(@Param("record") DepartmentInfo record, @Param("example") DepartmentInfoExample example);

    int updateByPrimaryKeySelective(DepartmentInfo record);

    int updateByPrimaryKey(DepartmentInfo record);
}