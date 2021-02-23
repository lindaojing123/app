package com.code.dao;

import com.code.entity.Video;
import com.code.po.VideoPO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {
    List<Video> selectVideoByPage(@Param("pageNo") Integer pageNo,
                                  @Param("pageSize") Integer pageSize);

    void insertVideo(Video video);

    List<VideoPO> queryByReleaseTime();
}