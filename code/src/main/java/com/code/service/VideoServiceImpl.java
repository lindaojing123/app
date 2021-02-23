package com.code.service;

import com.aliyun.oss.OSS;
import com.code.annotation.AddLog;
import com.code.dao.LikeMapper;
import com.code.dao.PlayMapper;
import com.code.dao.VideoMapper;
import com.code.entity.*;
import com.code.po.VideoPO;
import com.code.util.AliyunOSSUtils;
import com.code.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class VideoServiceImpl implements VideoService{
    @Resource
    VideoMapper videoMapper;
    @Resource
    PlayMapper playMapper;
    @Resource
    LikeMapper likeMapper;
    @Override
    public Map<String,Object> selectVideoByPage(Integer page, Integer rows) {

        Map<String,Object> map = new HashMap<>();
        VideoExample example = new VideoExample();
        Integer records = videoMapper.selectCountByExample(example);
        Integer total = records%rows==0?records/rows:records/rows+1;
        List<Video> videos = videoMapper.selectVideoByPage((page-1)*rows,rows);


        for(Video video : videos){
            PlayExample playExample = new PlayExample();
            playExample.createCriteria().andVideoIdEqualTo(video.getId());
            Play play = playMapper.selectOneByExample(playExample);
            if(play!=null){
                video.setPlayCount(play.getPlayCount());
            }else{
                video.setPlayCount(0);
            }

            LikeExample likeExample = new LikeExample();
            likeExample.createCriteria().andVideoIdEqualTo(video.getId());
            Integer count = likeMapper.selectCountByExample(likeExample);
            video.setLikeCount(count);
        }


        map.put("total",total);
        map.put("page",page);
        map.put("rows",videos);
        map.put("records",records);
        return map;
    }

    @Override
    //@AddLog(description = "添加视频信息")
    public String saveVideo(Video video) {
        String id = UUIDUtil.getUUID();
        video.setId(id);
        video.setStatus("1");
        video.setCreateTime(new Date());
        videoMapper.insertVideo(video);
        return id;
    }

    @Override
   // @AddLog(description = "修改视频信息")
    public void updateVideoAliyun(Video video) {
        String bucketName = "nasa-2021";
        Video videos = videoMapper.selectByPrimaryKey(video.getId());
        String isExistVideoPath = video.getVideoPath();
        if(isExistVideoPath!=null&&!isExistVideoPath.equals("")){
            String coverPath = videos.getCoverPath();
            String videoName = "video/"+videos.getVideoPath().split(Pattern.quote("/"))[videos.getVideoPath().split(Pattern.quote("/")).length-1];
            OSS ossClient = AliyunOSSUtils.getOssClient();
            boolean isVideo= ossClient.doesObjectExist(bucketName, videoName);
            System.out.println(isVideo);
            if(isVideo){
                ossClient.deleteObject(bucketName, videoName);
            }
            String coverName = "cover/"+coverPath.split(Pattern.quote("/"))[coverPath.split(Pattern.quote("/")).length-1];
            boolean  isCover= ossClient.doesObjectExist(bucketName, coverName);
            if(isCover){
                ossClient.deleteObject(bucketName, coverName);
            }
        }
        video.setVideoPath(videos.getVideoPath());
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    //@AddLog(description = "删除视频信息")
    public HashMap<String,String> deleteVideoAliyun(Video video) {
        HashMap<String,String> map = new HashMap<>();
        try{
            String bucketName = "nasa-2021";
            Video videos = videoMapper.selectByPrimaryKey(video);
            String videoPath = videos.getVideoPath();
            String coverPath = videos.getCoverPath();
            String videoName = "video/"+videoPath.split(Pattern.quote("/"))[videoPath.split(Pattern.quote("/")).length-1];
            String coverName = "cover/"+coverPath.split(Pattern.quote("/"))[coverPath.split(Pattern.quote("/")).length-1];

            OSS ossClient = AliyunOSSUtils.getOssClient();

            boolean isVideo= ossClient.doesObjectExist(bucketName, videoName);
            System.out.println(isVideo);
            if(isVideo){
                ossClient.deleteObject(bucketName, videoName);
            }

            boolean  isCover= ossClient.doesObjectExist(bucketName, coverName);
            if(isCover){
                ossClient.deleteObject(bucketName, coverName);
            }
            videoMapper.deleteByPrimaryKey(video.getId());
            map.put("status","200");
            map.put("message","删除成功");
        }catch (Exception e){
            map.put("status","400");
            map.put("message","删除失败");
        }
        return map;
    }

    @Override
    public void uploadUserVideo(MultipartFile videoPath, String id) {
        String filename =videoPath.getOriginalFilename();
        if(!filename.equals("")){
            //文件拼接时间戳  1611027120148-10.jpg
            System.out.println("id:"+id);
            String newName=new Date().getTime()+"-"+filename;
            String videoName = "video/"+newName;
            String coverName = "cover/"+newName.split("\\.")[0]+".jpg";
            AliyunOSSUtils.uploadFile(videoPath,"nasa-2021",videoName);
            try {
                AliyunOSSUtils.uploadFileByNet("nasa-2021",videoName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //3.执行修改
            Video video = new Video();
            video.setVideoPath("https://nasa-2021.oss-cn-hangzhou.aliyuncs.com/"+videoName);
            video.setId(id);
            video.setCoverPath("https://nasa-2021.oss-cn-hangzhou.aliyuncs.com/"+coverName);
            videoMapper.updateByPrimaryKeySelective(video);
        }
    }

    @Override
    public List<VideoPO> queryByReleaseTime() {
        List<VideoPO> videoPOS = videoMapper.queryByReleaseTime();
        for(VideoPO po:videoPOS){
             LikeExample likeExample = new LikeExample();
             likeExample.createCriteria().andVideoIdEqualTo(po.getId());
             Integer count = likeMapper.selectCountByExample(likeExample);
             po.setLikeCount(count);
        }
        return videoPOS;
    }
}
