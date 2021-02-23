package com.code.service;

import com.code.entity.Video;
import com.code.po.VideoPO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface VideoService {

    public Map<String,Object> selectVideoByPage(Integer page, Integer rows);

    String saveVideo(Video video);

    void updateVideoAliyun(Video video);

    HashMap<String,String> deleteVideoAliyun(Video video);

    void uploadUserVideo(MultipartFile headImg, String id) throws IOException;

    List<VideoPO> queryByReleaseTime();
}
