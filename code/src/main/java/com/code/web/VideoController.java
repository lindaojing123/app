package com.code.web;

import com.code.entity.Video;
import com.code.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("video")
@Controller
public class VideoController {

    @Autowired
    VideoService videoService;

    @RequestMapping("edit")
    @ResponseBody
    public Object edit(Video video,String oper){
        System.out.println("edit："+video);
        String id=null;
        if(oper.equals("add")){
            id = videoService.saveVideo(video);
        }if(oper.equals("edit")){
            videoService.updateVideoAliyun(video);
            id=video.getId();
        }if(oper.equals("del")){
            HashMap<String, String> map =  videoService.deleteVideoAliyun(video);
            return map;
        }
        return id;
    }

    @RequestMapping("findAll")
    @ResponseBody
    public Map<String,Object> findAll(Integer page, Integer rows){
        Map map = videoService.selectVideoByPage(page,rows);
        return map;
    }

    @RequestMapping("uploadUserVideo")
    @ResponseBody
    public void uploadUserVideo(MultipartFile videoPath, String id) throws IOException {
          videoService.uploadUserVideo(videoPath,id);
    }
}
