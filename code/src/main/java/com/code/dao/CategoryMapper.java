package com.code.dao;

import com.code.entity.Category;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {

    List<Category> selectCategoryByPage(@Param("pageNo") Integer page
            , @Param("pageSize") Integer rows);

    List<Category> select2Category(@Param("pageNo") Integer pageNo,
                                   @Param("pageSize")Integer rows,
                                   @Param("parentId")String parentId);
}