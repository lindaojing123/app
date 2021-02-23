package com.code.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.code.annotation.AddLog;
import com.code.dao.UserMapper;
import com.code.entity.Category;
import com.code.entity.User;
import com.code.util.AliyunOSSUtils;
import com.code.util.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserMapper userMapper;
    @Resource
    HttpServletRequest request;

    public List<User> findAll(Integer page, Integer rows){
        return userMapper.selectUserByPage((page-1)*rows,rows);
    }

    @Override
    public Integer total(Integer rows) {
        /**
         * 计算总页数： 信息总条数/ 每页展示的条数
         */
        Integer records = userMapper.getRecords();
        Integer total = null;
        if (records%rows==0) {
            total = records/rows;
        } else {
            total = records/rows+1;
        }
        return total;
    }

    @Override
    public Integer records() {
        return userMapper.getRecords();
    }

    @Override
    public void updateStatus(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    //@AddLog(description = "添加用户信息")
    public String saveUser(User user) {
        String uuid = UUIDUtil.getUUID();
        user.setId(uuid);
        user.setCreateTime(new Date());
        user.setWeChat(user.getPhone());
        user.setStatus("1");
        userMapper.insert(user);
        return uuid;
    }

    @Override
    public void updateUser(User user) {
        User users = userMapper.selectByPrimaryKey(user.getId());
        String image = users.getHeadImg();
        if(user.getHeadImg()!=null&&!user.getHeadImg().equals("")){
            String realPath = request.getSession().getServletContext().getRealPath("/upload/photo/"+image);
            File file = new File(realPath);
            if(file.exists()){
                file.delete();
                System.out.println("文件已删除。");
            }else{
                System.out.println("文件不存在！");
            }
        }
        user.setHeadImg(image);
        userMapper.updateByPrimaryKeySelective(user);

    }

    @Override
    public void deleteUser(User user) {
        User users = userMapper.selectOne(user);
        String image = users.getHeadImg();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo/"+image);
        File file = new File(realPath);
        if(file.exists()){
            file.delete();
            System.out.println("文件已删除。");
        }else{
            System.out.println("文件不存在！");
        }
        userMapper.deleteByPrimaryKey(user);
    }

    @Override
    public void uploadUserHeadImg(MultipartFile headImg, String id) {
        //1.获取文件名   10.jpg
        String filename = headImg.getOriginalFilename();
        if(!filename.equals("")){
            //2.根据相对路径获取绝对路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");

            File file = new File(realPath);
            //判断文件夹是否存在
            if(!file.exists()){
                file.mkdirs();  //创建文件夹
            }
            //文件拼接时间戳  1611027120148-10.jpg
            String newName=new Date().getTime()+"-"+filename;
            //3.文件上传
            try {
                headImg.transferTo(new File(realPath,newName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            User user = new User();
            user.setId(id);
            user.setHeadImg(newName);
            //4.执行修改
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    /*
     * 1.数据入库
     * 2.文件上传
     *     2-1 获取文件名  √
     *     2-2 将文件上传至阿里云  √
     *     2-3 修改图片路径  √
     * */
    @Override
    public void uploadUserHeadImgAliyun(MultipartFile headImg, String id) {
        //1.获取文件名   10.jpg
        String filename = headImg.getOriginalFilename();
        if(!filename.equals("")){
            //文件拼接时间戳  1611027120148-10.jpg
            String newName=new Date().getTime()+"-"+filename;
            String photoName = "photo/"+newName;
            String bucketName="nasa-2021";  //指定存储空间名称
            String objectName=photoName;   //指定文件名  目录名/文件名
            // 创建OSSClient实例。
            OSS ossClient = AliyunOSSUtils.getOssClient();

            // 上传Byte数组。
            byte[] bytes = null;
            try {
                //将MultipartFile 类型的headImg文件转为字节数组
                bytes = headImg.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));

            // 关闭OSSClient。
            ossClient.shutdown();
            //======

            //3.执行修改
            User user = new User();
            user.setId(id);
            user.setHeadImg(newName);
            System.out.println("newName:"+newName);
            System.out.println("id:"+id);

            userMapper.updateByPrimaryKeySelective(user);
        }

    }

    @Override
    //@AddLog(description = "删除用户信息")
    public void deleteUserHeadImgAliyun(User user) {
        User users = userMapper.selectOne(user);
        String image = users.getHeadImg();

        String bucketName = "nasa-2021";
        String objectName = "photo/"+image;

// 创建OSSClient实例。
        OSS ossClient = AliyunOSSUtils.getOssClient();
        boolean found = ossClient.doesObjectExist(bucketName, objectName);

// 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        if(found){
            ossClient.deleteObject(bucketName, objectName);
        }
        userMapper.deleteByPrimaryKey(user);
// 关闭OSSClient。
        ossClient.shutdown();

    }

    @Override
    //@AddLog(description = "修改用户信息")
    public void updateUserHeadImgAliyun(User user) {
        User users = userMapper.selectByPrimaryKey(user.getId());
        String image = users.getHeadImg();
        String bucketName = "nasa-2021";
        String objectName = "photo/"+image;
        // 创建OSSClient实例。
        OSS ossClient = AliyunOSSUtils.getOssClient();
        boolean found = ossClient.doesObjectExist(bucketName, objectName);
        System.out.println("found:"+found);
        if(user.getHeadImg()!=null&&!user.getHeadImg().equals("")){
            if(found){
                ossClient.deleteObject(bucketName, objectName);
                System.out.println("文件已删除。");
            }else{
                System.out.println("文件不存在！");
            }
        }
        user.setHeadImg(image);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public String selectAllUser() {
        List<User> users =userMapper.selectAll();
        StringBuilder sbu = new StringBuilder("<select>");
        for (User user:users) {
            sbu.append("<option value='"+user.getId()+"'>"+user.getUsername()+"</option>");
        }
        sbu.append("</select>");
        System.out.println(sbu.toString());
        return sbu.toString();
    }
}
