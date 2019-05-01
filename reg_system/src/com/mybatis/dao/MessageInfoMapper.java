package com.mybatis.dao;

import com.mybatis.model.MessageInfo;
import com.mybatis.model.MessageInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageInfoMapper {
    long countByExample(MessageInfoExample example);

    int deleteByExample(MessageInfoExample example);

    int deleteByPrimaryKey(String id);

    int insert(MessageInfo record);

    int insertSelective(MessageInfo record);

    List<MessageInfo> selectByExample(MessageInfoExample example);

    MessageInfo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MessageInfo record, @Param("example") MessageInfoExample example);

    int updateByExample(@Param("record") MessageInfo record, @Param("example") MessageInfoExample example);

    int updateByPrimaryKeySelective(MessageInfo record);

    int updateByPrimaryKey(MessageInfo record);
}