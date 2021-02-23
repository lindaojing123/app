package com.code.dao;

import com.code.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
     Integer getRecords();
     List<User> selectUserByPage(@Param("pageNo") Integer pageNo,
                                 @Param("pageSize") Integer pageSize);
}