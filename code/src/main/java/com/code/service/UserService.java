package com.code.service;

import com.code.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    public List<User> findAll(Integer page, Integer rows);
    public Integer total(Integer rows);
    public Integer records();
    public void updateStatus(User user);
    public String saveUser(User user);
    public void updateUser(User user);
    public void deleteUser(User user);
    public void uploadUserHeadImg(MultipartFile headImg, String id);
    public void uploadUserHeadImgAliyun(MultipartFile headImg, String id);
    public void deleteUserHeadImgAliyun(User user) ;
    public void updateUserHeadImgAliyun(User user) ;
    public String selectAllUser();
}
